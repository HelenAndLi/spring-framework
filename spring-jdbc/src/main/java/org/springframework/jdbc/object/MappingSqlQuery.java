/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.jdbc.object;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.lang.Nullable;

/**
 * Reusable query in which concrete subclasses must implement the abstract
 * mapRow(ResultSet, int) method to convert each row of the JDBC ResultSet
 * into an object.
 *
 * <p>Simplifies MappingSqlQueryWithParameters API by dropping parameters and
 * context. Most subclasses won't care about parameters. If you don't use
 * contextual information, subclass this instead of MappingSqlQueryWithParameters.
 *
 * @author Rod Johnson
 * @author Thomas Risberg
 * @author Jean-Pierre Pawlak
 * @param <T> the result type
 * @see MappingSqlQueryWithParameters
 *
 * 有可重用查询的具体子类的必须实现抽象的mapRow(ResultSet, int)方法，将JDBC ResultSet的每一行转换为一个对象
 * 通过删除参数和上下文来简化MappingSqlQueryWithParameters API。大多数子类不关心参数。如果你不使用上下文信息，子类this代替MappingSqlQueryWithParameters。
 */
public abstract class MappingSqlQuery<T> extends MappingSqlQueryWithParameters<T> {

	/**
	 * Constructor that allows use as a JavaBean.
	 */
	public MappingSqlQuery() {
	}

	/**
	 * Convenient constructor with DataSource and SQL string.
	 * @param ds the DataSource to use to obtain connections
	 * @param sql the SQL to run
	 */
	public MappingSqlQuery(DataSource ds, String sql) {
		super(ds, sql);
	}


	/**
	 * This method is implemented to invoke the simpler mapRow
	 * template method, ignoring parameters.
	 * @see #mapRow(ResultSet, int)
	 * 实现此方法是为了调用更简单的mapRow模板方法，忽略参数。
	 */
	@Override
	@Nullable
	protected final T mapRow(ResultSet rs, int rowNum, @Nullable Object[] parameters, @Nullable Map<?, ?> context)
			throws SQLException {

		return mapRow(rs, rowNum);
	}

	/**
	 * Subclasses must implement this method to convert each row of the
	 * ResultSet into an object of the result type.
	 * <p>Subclasses of this class, as opposed to direct subclasses of
	 * MappingSqlQueryWithParameters, don't need to concern themselves
	 * with the parameters to the execute method of the query object.
	 * @param rs the ResultSet we're working through
	 * @param rowNum row number (from 0) we're up to
	 * @return an object of the result type
	 * @throws SQLException if there's an error extracting data.
	 * Subclasses can simply not catch SQLExceptions, relying on the
	 * framework to clean up.
	 * 子类必须实现此方法，将ResultSet的每一行转换为结果类型的对象。
	 * 与MappingSqlQueryWithParameters的直接子类相反，该类的子类不需要关心查询对象的execute方法的参数。
	 */
	@Nullable
	protected abstract T mapRow(ResultSet rs, int rowNum) throws SQLException;

}
