package com.set.compiler.result;

import java.util.HashSet;
import java.util.Objects;
import java.util.Iterator;

public class Result {
public static void main (String args[]){
try{
main_program();} catch (Exception ex){ex.printStackTrace();}
}

private static int var=1;
private static int sub(int var3){
System.out.println(String.valueOf(var3));
return var3;
}
private static void main_program () {
System.out.println(String.valueOf(2));
int var2=3;
int var3=sub (var2);
Element el=new Element("elem");
Set s=new Set();
s.add(el);
s.add(el);
s.remove(el);
Set s2=Set.union(s,s);
System.out.println(String.valueOf(s));
for (Element e:s2){
System.out.println(String.valueOf(e));
}
while (var2<8){
var2=var2+2;
System.out.println(String.valueOf("Added"));
}
if (var2==8) {
System.out.println(String.valueOf("This is the right condition"));
}
 else {
System.out.println(String.valueOf("This is a wrong condition"));
}
}

}
class Element {

    private final String data;

    public Element(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return data;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Element) {
            return ((Element) obj).data.equals(data);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

}

class Set implements Iterable<Element> {

    private final java.util.Set<Element> set = new HashSet<>();

    public Set() {
    }

    public boolean add(Element element) {
        return set.add(element);
    }

    public boolean remove(Element element) {
        return set.remove(element);
    }

    public int size() {
        return set.size();
    }

    public void clear() {
        set.clear();
    }

    public static Set union(Set s1, Set s2) {
        Set out = new Set();
        out.set.addAll(s1.set);
        out.set.addAll(s2.set);
        return out;
    }


    public static Set intersection(Set s1, Set s2) {
        Set out = new Set();
        for (Element el : s1.set) {
            if (s2.set.contains(el)) {
                out.add(el);
            }
        }
        for (Element el : s2.set) {
            if (s1.set.contains(el)) {
                out.add(el);
            }
        }
        return out;
    }

    public static Set diff(Set s1, Set s2) {
        Set out = new Set();
        out.set.addAll(s1.set);
        for (Element el : s2.set) {
            if (s1.set.contains(el)) {
                out.remove(el);
            }
        }
        return out;
    }


    public static Set simDiff(Set s1, Set s2) {
        Set out = new Set();
        out.set.addAll(s1.set);
        out.set.addAll(s2.set);
        for (Element el : s1.set) {
            if (s2.set.contains(el)) {
                out.remove(el);
            }
        }
        for (Element el : s2.set) {
            if (s1.set.contains(el)) {
                out.remove(el);
            }
        }
        return out;
    }

    @Override
    public String toString() {
        return set.toString();
    }
    @Override
public Iterator<Element> iterator() {
   return set.iterator();
}
}
