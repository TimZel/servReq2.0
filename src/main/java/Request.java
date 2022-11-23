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
    public static String body;
    public static final Pattern patternHeaderValue = Pattern.compile("(\\p{Lu}{1,2}.*(\\:))(\\s.*)");

    public Request(String method, String path, String http, String contentType, HashMap<String, String> mapHeadersValues, String body) {
        this.method = method;
        this.path = path;
        this.http = http;
        this.contentType = contentType;
        this.mapHeadersValues = mapHeadersValues;
        this.body = body;
    }

    public static Request parse(InputStream isr) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(isr));
            String[] pathExeHttp = reader.readLine().split(" ");
            method = pathExeHttp[0];
            path = pathExeHttp[1];
            http = pathExeHttp[2];
            mapHeadersValues = new HashMap<>();
            String other = null;
            while (reader.ready()) {
                other = reader.readLine();
                int count = 0;//счетчик контроля поиска пустых строк
                Matcher matcherHeaderValue = patternHeaderValue.matcher(other);
                if (!(other.length() == 0)) {
                    if (matcherHeaderValue.find()) {
                        mapHeadersValues.put(matcherHeaderValue.group(1), matcherHeaderValue.group(3));
                        if (matcherHeaderValue.group(1).equals("Content-Type:")) {
                            contentType = matcherHeaderValue.group(3);
                        }
                    }
                } else if (count < 2) {
                    count++;
                }
            }
            body = other;
        return new Request(method, path, http, contentType, mapHeadersValues, body);
    }
}







//(^[^\n\p{Lu}].+) body
//(^\p{Lu}{3,10}[^\s\d\a-z]*) method
//(^\p{Lu}{1}[^\s\:]*)headers  (^\p{Lu}[^\s]*\:)
// (\:.+) :headersValues
//(^\p{Lu}[^\s]*\:.+) headers+values

//path  (\/[^\s]*)
//exe (\.[^\s]*)

//Pattern patternPathExeHttp = Pattern.compile("(^\\p{Lu}{3,7}.*)");
//Matcher matcherPathExeHttp = patternPathExeHttp.matcher(words[i]);

//        "POST /cgi-bin/process.cgi HTTPS/1.1" + CRLF +
//        "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)" + CRLF +
//        "Host: www.tutorialspoint.com"+ CRLF +
//        "Content-Type: application/x-www-form-urlencoded" + CRLF +
//        "Content-Length: length" + CRLF +
//        "Accept-Language: en-us"+ CRLF +
//        "Accept-Encoding: gzip, deflate"+ CRLF +
//        "Connection: Keep-Alive"+ CRLF +
//        CRLF +
//        "licenseID=string&content=string&/paramsXML=string" + CRLF;

//        Pattern patternExe = Pattern.compile("(\\.[^s]*)");
//        Matcher matcherExe = patternExe.matcher(path);
//        String exe = "";
//        if(matcherExe.find()) {
//            exe = matcherExe.group();
//        }

//        String request;
//        List<String> list = new ArrayList<>();
//        try {
//            while((request = br.readLine()) != null){
//                Scanner scanner = new Scanner(request);
//                while (scanner.hasNext()) {
//                   list.add(String.valueOf(scanner.nextLine().split(CRLF)));
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        for (int i = 0; i < list.size(); i++) {
//            Matcher matcherBody = patternBody.matcher(list.get(i));
//            Matcher matcherHeaderValue = patternHeaderValue.matcher(list.get(i));
//            pathExeHttp = list.get(0);
//            if(matcherHeaderValue.find()) {
//                mapHeadersValues.put(matcherHeaderValue.group(1), matcherHeaderValue.group(2));
//                if(matcherHeaderValue.group(1).equals("Content-Type:")) {
//                    contentType = matcherHeaderValue.group(2);
//                }
//            }
//            if(matcherBody.find()) {
//                body = list.get(i);
////            }
//        }