package uos.parse;

import java.util.List;
import java.util.Map;

import uos.Type;

public interface Methods {
	public void setName( String methodName );
	public String getName();
	
	public void setReturnType( Type type );
	public Type getReturnType();
	
	public void setContents( String contents );
	public String getContents();
	
	public void setParam( Type type, String nameOfParam );
	public Map<String, Type> getParams();
	public Type getParamType( String nameOfParam );
	
	public void setMember( Members members );
	public List<Members> getMemberList(int index);
	public void clearMemberList();
	public Members getMember( int index );
	public void removeMember( int index );
	public void removeMember( Members members );
}
