package com.querylayer;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class JojoResult{
	private List<?> result; // Parameterize the list with T
	private int index = -1;
    private final Class<?> clazz;
    private ResultSet rs;

	public JojoResult(ResultSet rs, Class<?> clazz) throws ClassNotFoundException, SQLException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, NoSuchFieldException {
		this.result = Executor.executeSelect(rs, clazz).execute();
		this.rs = rs;
		this.clazz = clazz;
	}

	public boolean next() {
		if (result == null) {
			return false;
		}
		if (index >= result.size() - 1) {
			return false;
		}
		index++;
		return true;
	}

	@SuppressWarnings("unchecked")
	public <T> T getPojo() {
        if (result == null || index < 0 || index >= result.size()) {
            return null;
        }
        return (T) clazz.cast(result.get(index));
    }

	public int getIndex() {
		return this.index;
	}
}
