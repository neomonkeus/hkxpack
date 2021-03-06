package com.dexesttp.hkxpack.xml.classxml.definition;

public class ResolvedClass extends ClassXML {
	protected final String classname;
	protected final int classID;
	
	public ResolvedClass(String classname, int classID) {
		this.classname = classname;
		this.classID = classID;
	}

	@Override
	public String getClassName() {
		return classname;
	}

	@Override
	public int getClassID() {
		return classID;
	}
}
