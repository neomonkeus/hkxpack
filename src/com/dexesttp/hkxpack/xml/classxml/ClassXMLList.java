package com.dexesttp.hkxpack.xml.classxml;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.dexesttp.hkxpack.hkx.definition.ClassName;
import com.dexesttp.hkxpack.resources.ByteUtils;
import com.dexesttp.hkxpack.xml.classxml.definition.ClassXML;
import com.dexesttp.hkxpack.xml.classxml.definition.EnumObj;
import com.dexesttp.hkxpack.xml.classxml.definition.ImportedClass;
import com.dexesttp.hkxpack.xml.classxml.definition.ResolvedClass;

public class ClassXMLList {
	private static ClassXMLList instance;

	private Stack<ClassName> toRead = new Stack<>();
	private Stack<ImportedClass> toResolve = new Stack<>();
	private Map<String, ResolvedClass> classMap = new HashMap<>();
	private Map<String, EnumObj> enumMap = new HashMap<>();
	
	private ClassXMLList() {
	}
	
	public static ClassXMLList getInstance() {
		if(instance == null)
			instance = new ClassXMLList();
		return instance;
	}

	public void addClass(ClassName inst) {
		toRead.push(inst);
	}
	
	public void addClass(ImportedClass inst) {
		toResolve.push(inst);
	}
	
	public void import_classes() throws IOException {
		while(!toRead.isEmpty()) {
			ClassName cname = toRead.pop();
			toResolve.push(ClassXMLReader.getClassFromFile(cname.className, ByteUtils.getInt(cname.classCode)));
		}
	}
	
	public void resolve() throws IOException {
		while(!toResolve.isEmpty()) {
			ImportedClass classObject = toResolve.pop();
			classMap.put(classObject.getClassName(), classObject.resolve());
		}
	}
	
	public ClassXML get(String classname) {
		return classMap.get(classname);
	}
	
	public void addEnum(String className, String enumName, EnumObj enumObj) {
		enumMap.put(className + "." + enumName, enumObj);
	}
	
	public EnumObj getEnum(String className, String enumName) {
		return enumMap.get(className + "." + enumName);
	}
}
