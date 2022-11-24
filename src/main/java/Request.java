import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {
    String method;
    String path;
    String http;
    String contentType;
    HashMap<String, String> mapHeadersValues;
    List<String> bodyList;
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
        String methodParse = pathExeHttp[0];
        String pathParse = pathExeHttp[1];
        String httpParse = pathExeHttp[2];
        HashMap<String, String> mapHeadersValuesParse = new HashMap<>();
        List<String> bodyListParse = new ArrayList<>();
        String contentTypeParse = null;

            String other;//строка дл считывания заголовков протокола и тела
            int count = 0;//счетчик контроля поиска пустых строк
            while (reader.ready()) {
                other = reader.readLine();
                Matcher matcherHeaderValue = patternHeaderValue.matcher(other);
                if (!(other.length() == 0)) {
                    if(count > 0) {
                        bodyListParse.add(other);//формирую боди
                    }
                    if (matcherHeaderValue.find()) {
                        mapHeadersValuesParse.put(matcherHeaderValue.group(1), matcherHeaderValue.group(3));
                        if (matcherHeaderValue.group(1).equals("Content-Type:")) {
                            contentTypeParse = matcherHeaderValue.group(3);
                        }
                    }
                } else if (count >= 0) {
                    count++;
                }
            }
        return new Request(methodParse, pathParse, httpParse, contentTypeParse, mapHeadersValuesParse, bodyListParse);
    }
}






