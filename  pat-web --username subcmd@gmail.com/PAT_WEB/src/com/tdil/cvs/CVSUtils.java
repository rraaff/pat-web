package com.tdil.cvs;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class CVSUtils {

	private static String databaseLocation = "/home/mgodoy/icarus/workspace/simon/PAT_WEB/database/";
	
	@SuppressWarnings("unchecked")
	public synchronized static void read(String filename, String headers[], Class aClass, List result) throws IOException, IllegalAccessException, InvocationTargetException, InstantiationException {
		CsvReader cvsFile = null;
		try {
			cvsFile = new CsvReader(databaseLocation + filename);
			cvsFile.readHeaders();
			while(cvsFile.readRecord()) {
				Map<String, Object> row = new HashMap<String, Object>();
				for (String st : headers) {
					row.put(st, cvsFile.get(st));
				}
				Object bean = aClass.newInstance();
				BeanUtils.populate(bean, row);
				result.add(bean);
			}
		} finally {
			if (cvsFile != null) {
				cvsFile.close();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public synchronized static void write(String filename, String headers[], List data) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		CsvWriter csvOutput = null;
		try {
			csvOutput = new CsvWriter(new FileWriter(databaseLocation + filename, false), ',');
			// if the file didn't already exist then we need to write out the header line
			for (String st : headers) {
				csvOutput.write(st);
			}
			csvOutput.endRecord();
			for (Object row : data) {
				Map<String, Object> rowMap = BeanUtils.describe(row);
				for (String st : headers) {
					csvOutput.write(rowMap.get(st).toString());
				}
				csvOutput.endRecord();
			}
		} finally {
			if (csvOutput != null) {
				csvOutput.close();
			}
		}
	}
	
}
