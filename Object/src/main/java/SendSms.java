import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendSms {

   public static void main(String[] args) {
      String urlWavy = "https://api-messaging.wavy.global/v1/send-sms";
//      String urlWavy = "https://api-messaging.wavy.global/v1/sms/status/search";

      String userName = "horacio_t1@verifone.com";
      String authenticationToken = "CzXLQAfp9DxM63hPszFoTKv5dSFCVJvAesviG9KF";
      String urlVoucher = "https://us3int.tmerchantportal.com/gmp-web/rest/public/onlinevoucher?hash=a768c4d46ae58b473b4fbb5daf0517307619080b";


      String body = "{\"destination\": \"59893973068\" ,  \"messageText\": \"Rene\\nquebrada\"}";

//      String body = "{\"destination\": \"59894020991\", \"messageText\": \"Tienes un nuevo voucher de compra. Puedes descargarlo desde la siguiente liga:\" + \"urlVoucher"}";
      System.out.println(body);

      String response = doPost(urlWavy, body, userName, authenticationToken);
      System.out.format("\n(%s): \n\t\t%s", response,body);
   }

   public static String doPost(String strUrl, String request, String userName, String authenticationToken) {
      HttpURLConnection conn = null;
      OutputStreamWriter wr = null;
      BufferedReader br = null;
      try {
         URL url = new URL(strUrl);

         conn = (HttpURLConnection) url.openConnection();
         conn.setRequestMethod("POST");
         conn.setDoOutput(true);
         conn.setUseCaches(false);
         //conn.setInstanceFollowRedirects(true);
         conn.setConnectTimeout(30000);
         conn.setReadTimeout(30000);

         conn.setRequestProperty("Content-Type", "application/json");
         conn.setRequestProperty("UserName", userName);
         conn.setRequestProperty("AuthenticationToken", authenticationToken);

         // write the request
         wr = new OutputStreamWriter(conn.getOutputStream());
         wr.write(request);
         wr.close();

         // read the response
         br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

         StringBuilder resp = new StringBuilder();
         String line;
         while ((line = br.readLine()) != null) {
            resp.append(line).append("\n");
         }
         return resp.toString();

      } catch (IOException e) {
         e.printStackTrace();
      } finally {
         try {
            if (wr != null) {
               wr.close();
            }
            if (br != null) {
               br.close();
            }
            if (conn != null) {
               conn.disconnect();
            }
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
      return null;
   }
}
