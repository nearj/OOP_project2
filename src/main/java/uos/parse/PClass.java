package uos.parse;

import java.util.List;
import java.util.ArrayList;

public final class PClass implements Classes {
	
	private static List<PClass> classList = new ArrayList<>();
	private static int classIdx = 0;
	private String className;
	private List<Methods> methodList = new ArrayList<>();
	private List<Members> memberList = new ArrayList<>();
	private PClass() {} // To prevent instantiation
	
	public static PClass newInstance() {
		classList.add(new PClass());
		return classList.get(classIdx++);
	}

	public static int getClassNum() {
		return classIdx;
	}
	
	@Override
	public List<PClass> getClassList() {
		return classList;
	}

	@Override
	public void setName( String className ) {
		this.className = className;
	}

	@Override
	public String getName() {
		return className;
	}

	@Override
	public void setClass( PClass pClass ) {
	}

	@Override
	public PClass getKlass() {
		return null;
	}

	@Override
	public void setMember( Members members ) {
		memberList.add(members);
	}

	@Override
	public List<Members> getMemberList() {
		return memberList;
	}

	@Override
	public Members getMember( int index ) {
		return memberList.get(index);
	}

	@Override
	public void setMethod( Methods methods ) {
		methodList.add(methods);
	}

	@Override
	public List<Methods> getMethodList() {
		return methodList;
	}

	@Override
	public Methods getMethod( int index ) {
		return methodList.get(index);
	}


	@Override
	public void removeMember(int index) {
		memberList.remove(index);
	}

	@Override
	public void clearMemberList() {
		memberList.clear();
	}

	@Override
	public void removeMember(Members members) {
		memberList.remove(members);		
	}

	@Override
	public void clearMethodList() {
		methodList.clear();
	}

	@Override
	public void removeMethod(int index) {
		methodList.remove(index);
	}

	@Override
	public void removeMethod(Methods methods) {
		methodList.remove(methods);		
	}

}
