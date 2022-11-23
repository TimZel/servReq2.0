import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class client {
    public static final String r1 =
"POST /home/zelyanin/Desktop/testFolder/test2.txt HTTP/1.0" + "\r\n" +
"User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)" + "\r\n" +
"Host: www.tutorialspoint.com" + "\r\n" +
"Content-Type: application/x-www-form-urlencoded" + "\r\n" +
"Content-Length: length" + "\r\n" +
"Accept-Language: en-us" + "\r\n" +
"Accept-Encoding: gzip, deflate" + "\r\n" +
"Connection: Keep-Alive" + "\r\n"  + "\r\n" +
"licenseID=string&content=string&/paramsXML=string" + "\r\n";
    public static final String r2 =
"POST /home/zelyanin/Desktop/testFolder/rocket.txt HTTP/1.1" + "\r\n" +
"User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)" + "\r\n" +
"Host: www.tutorialspoint.com" + "\r\n" +
"Content-Type: text/xml; charset=utf-8" + "\r\n" +
"Content-Length: length" + "\r\n" +
"Accept-Language: en-us" + "\r\n" +
"Accept-Encoding: gzip, deflate" + "\r\n" +
"Connection: Keep-Alive" + "\r\n" +
"\r\n" +
"<?xmrdl vedrrsidon=\"1.d0\" enrcodding=\"utrf-8\"?>" + "\r\n" +
"<strirng xhmdglrns=\"h/\">strdring</strrding>" + "\r\n";

    public static void main(String[] args) throws IOException {
        Socket client = new Socket("localhost", 12000);//создаю сокет подключения к серверу
        //с заданными портом и айпи
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));//открываю выводящий поток для запросов
        Scanner reader = new Scanner(client.getInputStream());//открываю входящий поток для считывания ответов сервера

        writer.write(r1); //отправляю запрос (если менять в строке вид метода, то будет разный результат)
        writer.flush();//сбрасываю поток

        while(reader.hasNext()) { //вывожу в консоль ответ сервера
            System.out.println(reader.nextLine());//вывожу инфо в консоль
        }
        //закрываю потоки
        reader.close();
        writer.close();
        client.close();
    }
}