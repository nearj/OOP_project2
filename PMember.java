package uos.parse;

public class PMember implements Members,Parse{
	
	private Classes refClass;
	private String refMethod;
	private String memberName;
	private Type memberType;
	private Map<String, Type> params = new HashMap<>();
	private List<Members> memberList =  new ArrayList<>();
	
	private PMember() {}
	
	
	public static PMember newInstance() {
		return new PMember();
	}
	
	@Override
	public void setRefClass(Classes classes) {
		this.refClass=classes;
	}
	
	@Override
	public Classes getRefClass(){
		return refClass;
	}
	
	@Override
	public void setRefMethod(Methods methods) {
		this.refMethod = methods;
	}
	
	@Override
	public String getRefMethod() {
		return refMethod;
	}
	 
	@Override
	public List<Methods> getRefMethodList(){
		
	}
	
	@Override
	public void setMemberType(Type type){
		this.memberType=type;
	}
	
	@Override
	public Type getMemberType(){
		return memberType;
	}
	
	@Override
	public void setMemberName(String name){
		this.memberName = name;
	}
	
	@Override
	public String getMemberType(){
		return memberType;
	}
	
	@Override
	public String findUsedMethods(String memberName) {
		
	}
	
	
	
	
	
	
	