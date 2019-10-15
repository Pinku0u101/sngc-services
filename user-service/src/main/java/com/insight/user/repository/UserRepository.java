package com.insight.user.repository;

import com.insight.user.model.LoginDetails;
import com.insight.user.model.User;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.core.record.OElement;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultSet;

public class UserRepository
{
    public void createConnection(User user)
    {
        OrientDB orientDB = new OrientDB("plocal:/Users/prakashp/sngc-insights/sngc-insights/orientdb-3.0.22/databases/", OrientDBConfig.defaultConfig());

        ODatabaseSession db = orientDB.open("Insights", "admin", "admin");

        try {
            OElement userVertex = db.newInstance("Users");
            userVertex.setProperty("id", user.getId());
            userVertex.setProperty("age",user.getAge());
            userVertex.setProperty("firstName",user.getFirstName());
            userVertex.setProperty("lastName",user.getLastName());
            //userVertex.setProperty("gender", Gender.FEMALE.toString());
            userVertex.setProperty( "email", user.getEmail());
            userVertex.setProperty("phoneNumber", user.getPhoneNumber());
            userVertex.setProperty("userName", user.getUserName());
            userVertex.setProperty( "password", user.getPassword());

            userVertex.save();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
        finally {
            db.close();
            orientDB.close();
        }



    }

    public LoginDetails getUserDetails( LoginDetails loginDetails)
    {
        OrientDB orientDB = new OrientDB("plocal:/Users/prakashp/sngc-insights/sngc-insights/orientdb-3.0.22/databases/", OrientDBConfig.defaultConfig());

        ODatabaseSession db = orientDB.open("Insights", "admin", "admin");

        LoginDetails loginDetailsFromDb = null;
        try {
            String statement = "SELECT FROM Users WHERE userName = ? and password = ?";
            OResultSet rs = db.query(statement, loginDetails.getUserName(), loginDetails.getPassword() );
            while(rs.hasNext()){
                OResult row = rs.next();

                loginDetailsFromDb = new LoginDetails( row.getProperty("userName"), row.getProperty("password"), row.getProperty("firstName") );

            }
            rs.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        finally {

            db.close();
            orientDB.close();
        }

        return loginDetailsFromDb;
    }
}
