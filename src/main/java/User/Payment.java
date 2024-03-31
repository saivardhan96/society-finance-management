package User;

import java.sql.*;

public class Payment {
    final Connection con;
    private PreparedStatement ps;
    private ResultSet resultSet;
    public ResultSet finRs; private String userName;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Payment() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sai","root","Kittu@96");

    }

    public String getUserName() {
        return userName;
    }

    public void currStatus(){

    }
    public void sse(){
        System.out.println("SaiVardhanPonduru");
    }

    public void isDue(String uname) throws SQLException {
        this.ps = this.con.prepareStatement("update financetrail set due_times = due_times+1 where uname='vicky'");
        this.ps.setString(1,uname);
    }

    public void getDelayedEmails() throws SQLException {
        this.ps = this.con.prepareStatement("select email_id,phone_number from familytrail where uname in (select uname from financetrail where due_times>1)");
        this.resultSet = this.ps.executeQuery();

/*        while (this.resultSet.next()){
            System.out.println("Email: "+resultSet.getString("email_id")+" Phone: "+resultSet.getString("phone_number"));
        }*/

    }


    public void registerNew(String username, String passcode) throws SQLException {
        this.ps = this.con.prepareStatement("insert into logins values(?,?)");
        this.ps.setString(1,username);
        this.ps.setString(2,passcode);
    }

}
