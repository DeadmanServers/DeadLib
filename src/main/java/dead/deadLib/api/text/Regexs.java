package dead.deadLib.api.text;

public class Regexs {

    public static Boolean isProperName(String name){
        return name.matches("^[A-Za-z0-9]+(?:[ _!?-][A-Za-z0-9]+)*$");
    }
}
