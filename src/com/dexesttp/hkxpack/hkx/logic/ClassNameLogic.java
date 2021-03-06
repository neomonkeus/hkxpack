package com.dexesttp.hkxpack.hkx.logic;

import java.io.IOException;

import com.dexesttp.hkxpack.hkx.classes.ClassMapper;
import com.dexesttp.hkxpack.hkx.definition.ClassName;
import com.dexesttp.hkxpack.hkx.reader.ClassNamesReader;
import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;
import com.dexesttp.hkxpack.xml.classxml.ClassXMLList;

public class ClassNameLogic {
	private ClassNamesReader reader;

	public ClassNameLogic(ClassNamesReader reader) throws UninitializedHKXException {
		if(!reader.isConnected())
			throw new UninitializedHKXException();
		this.reader = reader;
	}
	
	public ClassMapper resolve() throws IOException {
		ClassMapper mapper = new ClassMapper();
		ClassXMLList classXMLList = ClassXMLList.getInstance();
		ClassName[] cNameList = reader.read();
		// Retrieve class data.
		for(ClassName inst : cNameList) {
			classXMLList.addClass(inst);
			mapper.add(inst.position, inst);
		}
		classXMLList.import_classes();
		classXMLList.resolve();
		return mapper;
	}
}
