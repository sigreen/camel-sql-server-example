package sample.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;

@Component
public class ReportRouter extends RouteBuilder {
    
    @Autowired
    DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(AbstractDataSource dataSource) {
        this.dataSource = dataSource;
        }

    @Override
    public void configure() {        
        from("timer:myTimer?fixedRate=true&period=5000")
        .setBody(simple("select top 10 * from dbo.sqlServerInstance"))
        .log("SQL: ${body}")
        .to("jdbc:dataSource")
        .log("Out: ${body}");
    }

}
