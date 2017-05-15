/**
 * 
 */
package dataTest;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import server.DBHelper.DBHelper;

/**
 * @author 朱应山
 *
 */
public class QueryTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws SQLException {	
		String sql="select * from user where userID='zhuyingshan';";
		DBHelper dbHelper=new DBHelper(sql);
		ResultSet rs=dbHelper.pst.executeQuery();
		while (rs.next()) {
			assertEquals(rs.getString(2),"123456");
			assertEquals(rs.getString(3), "m");
		}
		dbHelper.close();
	}

}
