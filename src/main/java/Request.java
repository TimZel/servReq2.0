import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {
    public static String method;
    public static String path;
    public static String http;
    public static String contentType;
    public static HashMap<String, String> mapHeadersValues = new HashMap<>();
    public static List<String> bodyList = new ArrayList<>();
    public static final Pattern patternHeaderValue = Pattern.compile("(\\p{Lu}{1,2}.*(\\:))(\\s.*)");

    public Request(String method, String path, String http, String contentType, HashMap<String, String> mapHeadersValues, List<String> bodyList) {
        this.method = method;
        this.path = path;
        this.http = http;
        this.contentType = contentType;
        this.mapHeadersValues = mapHeadersValues;
        this.bodyList = bodyList;
    }

    public static Request parse(InputStream isr) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(isr));
            String[] pathExeHttp = reader.readLine().split(" ");
            method = pathExeHttp[0];
            path = pathExeHttp[1];
            http = pathExeHttp[2];
            mapHeadersValues = new HashMap<>();
            if(bodyList.size() > 0) {// если в теле что-то есть, удаляем
                bodyList.removeAll(bodyList);
            }
            String other = null;//строка дл считывания заголовков протокола и тела
            int count = 0;//счетчик контроля поиска пустых строк
            while (reader.ready()) {
                other = reader.readLine();
                Matcher matcherHeaderValue = patternHeaderValue.matcher(other);
                if (!(other.length() == 0)) {
                    if(count > 0) {
                        bodyList.add(other);//формирую боди
                    }
                    if (matcherHeaderValue.find()) {
                        mapHeadersValues.put(matcherHeaderValue.group(1), matcherHeaderValue.group(3));
                        if (matcherHeaderValue.group(1).equals("Content-Type:")) {
                            contentType = matcherHeaderValue.group(3);
                        }
                    }
                } else if (count >= 0) {
                    count++;
                }
            }
        return new Request(method, path, http, contentType, mapHeadersValues, bodyList);
    }
}







