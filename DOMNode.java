import java.util.ArrayList;
import java.util.HashMap;


public class DOMNode {
	private HashMap<String, String> attributes;
	private String text;
	private String tagName;
	private ArrayList<DOMNode> children;
	
	public DOMNode(String tag, String text){
		this.text = text;
		this.attributes = new HashMap<String, String>();
		this.children = new ArrayList<DOMNode>();
		
		parseTag(tag);
	}
	
	private void parseTag(String tag){
		if(Tokenizer.isClosingTag(tag)){
			tag = tag.substring(2, tag.length() - 1);						
		}else{
			tag = tag.substring(1, tag.length() - 1);			
		}
		
		tag = tag.replaceAll(" *= *", "=");
		
		ArrayList<String> components = new ArrayList<String>();
		StringBuilder sb = new StringBuilder(50);
		
		//retrieve tag name
		int j = 0;
		while(j < tag.length() && tag.charAt(j) != ' '){
			sb.append(tag.charAt(j));
			j++;
		}
		this.tagName = sb.toString();
		sb = new StringBuilder(50);
		
		//retrieve attributes
		int quoteCount = 0;
		for(int i = j; i < tag.length(); i++){
			char c = tag.charAt(i);
			
			//ignore spaces between atrributes
			if(c==' ' && quoteCount != 1){
			}else{
				sb.append(c);				
			}
			
			if(c=='"' || c=='\''){
				quoteCount++;
			}
			if(quoteCount == 2){
				quoteCount = 0;
				components.add(sb.toString());
				sb = new StringBuilder(50);
			}
		}
		
		for(int i = 1; i < components.size(); i++){
			String[] attrib = components.get(i).split("=");
			String attributeName = attrib[0];
			String attributeValue = attrib[1].substring(1, attrib[1].length() - 1);
			this.attributes.put(attributeName, attributeValue);
		}
	}
	
	public void addAttribute(String attrib, String value){
		attributes.put(attrib, value);
	}
	
	public HashMap<String, String> getAttributes(){
		return attributes;
	}
	
	public void appendText(String str){
		this.text = this.text + str;
	}

	public String getText(){
		return text;
	}
	
	public String getTagName(){
		return tagName;
	}
	
	public ArrayList<DOMNode> getChildren(){
		return children;
	}
	
	public boolean isClosingTag(){
		return (tagName.startsWith("</") && tagName.endsWith(">"));
	}
	
	public String genClosingTag(){
		return "</" + tagName + ">";
	}
	
	public void printTree(){
		printTreeRec(this);
		System.out.println(this.tagName);
	}
	
	private void printTreeRec(DOMNode curr){
		for(DOMNode child : curr.getChildren()){
			printTreeRec(child);
			if(child.getText().isEmpty()){
				System.out.println(child.getTagName() + " ");								
			}else{
				System.out.println(child.getTagName() + " - text: " + child.getText() + " ");				
			}
		}
	}
	
}
