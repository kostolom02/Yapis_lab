grammar SetLanguage;

program : 'main_program' block;

block :BEGIN statement* END;

statement :
assign_var |
assign_set |
operations |
assign_var_method_invocation |
while_cycle |
if_then |
method_invocation |
print |
operations_with_set |
for_each;

BEGIN : 'begin';
SET : 'set';
ELEMENT : 'element';
ADD:'add';
REMOVE:'remove';
END : 'end';
PRINTF: 'print';
INT : 'int';
WHILE: 'while';
FOR: 'for';
CONST: 'const';
IF : 'if';
THEN : 'then';
CALL : 'call';
RETURN : 'return';
SEPARATOR : ';';
DELIMITER : '.';
COLON : ':';
GLOBAL: 'global';

ID : [a-zA-Z_][a-zA-Z_0-9]*;
STRING : '"'(.)+?'"';
NUMBER : [0-9]+;
WS     : [ \n\t\r]+ -> skip;

PLUS: '+';
MINUS: '-';
MULTIPLY: '*';
DIVIDE: '/';

NEGATION : '!';
EQUAL: '==';
NON_EQUAL: '!=';
LESS: '<';
LESS_OR_EQUALS: '<=';
GREATER: '>';
GREATER_OR_EQUALS: '>=';

O_BRACKET : '(';
C_BRACKET : ')';

K_O_BRACKET : '{';
K_C_BRACKET : '}';

digit_expression:	digit_expression (MULTIPLY|DIVIDE) digit_expression
    |	digit_expression (PLUS|MINUS) digit_expression
    |	ID
    |	O_BRACKET digit_expression C_BRACKET
    | NUMBER
    ;

initialize_set: (K_C_BRACKET K_O_BRACKET)|(K_O_BRACKET (ID ',')* (ID) K_C_BRACKET)|(ID(MULTIPLY|DIVIDE|PLUS|MINUS)ID);
initialize_element: STRING;

assign_var_method_invocation : type CONST? ID '=' method_invocation;
assign_set : SET CONST? ID '=' (initialize_set) SEPARATOR;
assign_var : type_1 CONST? ID '=' (digit_expression|initialize_element) SEPARATOR;
print : PRINTF O_BRACKET (ID|NUMBER|STRING) C_BRACKET SEPARATOR;
operations : ID '=' digit_expression SEPARATOR;
operations_with_set: ID DELIMITER (ADD|REMOVE) (O_BRACKET ID C_BRACKET) SEPARATOR;

simple_compare : (digit_expression) (EQUAL|NON_EQUAL|LESS|GREATER|LESS_OR_EQUALS|GREATER_OR_EQUALS) (digit_expression);
hard_compare : NEGATION? O_BRACKET simple_compare C_BRACKET;

while_cycle: WHILE O_BRACKET (simple_compare|hard_compare) C_BRACKET block;
if_then: IF O_BRACKET (simple_compare|hard_compare) C_BRACKET block THEN block;
for_each : FOR O_BRACKET ELEMENT ID COLON ID C_BRACKET block;

global_assign_set : GLOBAL assign_set;
global_assign_var : GLOBAL assign_var;

type: INT|ELEMENT|SET;
type_1: INT|ELEMENT;
signature: (O_BRACKET (type ID ',')* (type ID) C_BRACKET);
subprogram_return : 'sub_program' type ID (signature|(O_BRACKET C_BRACKET)) block_return;
subprogram_non_return : 'sub_program' ID (signature|(O_BRACKET C_BRACKET)) (block_non_return|block);

block_return : BEGIN statement* RETURN ID SEPARATOR END;
block_non_return : BEGIN statement* RETURN SEPARATOR END;

signature_method_invocation: (O_BRACKET (ID ',')* (ID) C_BRACKET);
method_invocation : CALL ID (signature_method_invocation|(O_BRACKET C_BRACKET)) SEPARATOR;

global_program: (global_assign_set|global_assign_var)*program{1}(subprogram_non_return|subprogram_return)*;