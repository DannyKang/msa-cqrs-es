package msacqrses.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

/**
 * View Controller for Cart.
 */
@RestController
public class ViewController {

    @Autowired
    private DataSource dataSource;

/*    @RequestMapping(value = "/view", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Map<String, Double>> getAccounts() {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Double>> queryResult = jdbcTemplate.query("SELECT * from account_view ORDER BY account_no", (rs, rowNum) -> {
            return new HashMap<String, Double>() {{
                put(rs.getString("ACCOUNT_NO"), rs.getDouble("BALANCE"));
            }};
        });

        return queryResult;
    }
*/
    
    @RequestMapping(value = "/view", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getItems() {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Integer>> queryResult = jdbcTemplate.query("SELECT * from cartview ORDER BY cartid", (rs, rowNum) -> {
            return new HashMap<String, Integer>() {{
                put(rs.getString("CARTID"), rs.getInt("ITEMS"));
            }};
        });

		if (queryResult.size() > 0) {
			return new ResponseEntity<>(queryResult, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

    }

}
