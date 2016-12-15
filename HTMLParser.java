

public class HTMLParser {
	private static Tokenizer tokenizer;
	
	//TEST CODE
	public static void main(String[] args){
		//String html = Tokenizer.readHTML("http://taligarsiel.com/ClientSidePerformance.html");
		String html = "<html><body><div class='maintext'><p>Hello World</p></div><a href='abc.com'>Link</a></body></html>";
		
		tokenizer = new Tokenizer(html);
		DOMNode tree = parse();
		tree.printTree();
	}
	
	public static DOMNode parse(){
		String token = tokenizer.get();
		while(!Tokenizer.isTag(token)){ //in case the code doesn't begin with <html> tag
			token = tokenizer.get();
		}
		DOMNode root = new DOMNode(token, "");
		recurs(root);
		
		return root;
	}
	
	private static void recurs(DOMNode current){
		String token = tokenizer.get();
		
		while(!current.genClosingTag().equals(token)){
			if(Tokenizer.isTag(token)){
				DOMNode child = new DOMNode(token, "");
				recurs(child);
				current.getChildren().add(child);
			}else{//token is text
				current.appendText(token);
			}
			token = tokenizer.get();
		}
		
	}
}
