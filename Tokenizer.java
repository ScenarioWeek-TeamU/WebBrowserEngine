import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class Tokenizer {
	private String source;
	private ArrayList<String> tokens;
	
	public Tokenizer(String source){
		this.source = source.replace("\n", "").replace("\r", "");
		this.tokens = new ArrayList<String>();
		tokenize();
	}
	
	private void tokenize(){
		int i = 0;
		String token = "";
		char c = source.charAt(i);
		while(i < source.length()){
			c = source.charAt(i);
			if (c == '<') {
				while (c != '>' && i < source.length()) {
					c = source.charAt(i);
					token = token + c;
					i++;
				}
				tokens.add(token);
				token = "";
			} else {
				while (c != '<' && i < source.length()) {
					token = token + c;
					i++;
					c = source.charAt(i);
				}
				tokens.add(token);
				token = "";
			}
		}
	}
	
	public String get(){
		if(tokens.isEmpty()){
			return "";
		}else{
			return tokens.remove(0);
		}
	}
	
	public static String readHTML(String link){
		URL url;
		String html = "";

        try {
            url = new URL(link);
            URLConnection conn = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            String line;
            while ((line = br.readLine()) != null) {
                    html = html + line + "\n";
            }
            br.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return html;
	}
	
	public static boolean isTag(String str){
		return (str.contains("<") && str.contains(">"));
	}
	public static boolean isClosingTag(String str){
		return (str.startsWith("</") && str.endsWith(">"));
	}
	public static boolean isText(String str){
		return !isTag(str);
	}
}
