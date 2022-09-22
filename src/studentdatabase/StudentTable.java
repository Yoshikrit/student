/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentdatabase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.DatabaseHandler;
/**
 *
 * @author acer
 */
public class StudentTable {
    public static int updateStudent(DatabaseHandler dbHandler, Student stu) {
        String sql = "update employee set name=?, student=? where id=?";
        int rowUpdated;
        try {
            rowUpdated = dbHandler.update(sql, stu.getName(), stu.getGPA(), stu.getId());
        }
        catch (SQLException ex ) {
            
            rowUpdated = 0;
        }
        
        return rowUpdated;
    }
     public static int removeStudent(DatabaseHandler dbHandler, Student stu) {
         String sql ="delete from student where id = ?";
         
         int rowDeleted;
         try {
            rowDeleted = dbHandler.update(sql, stu.getId());
         }
         catch (SQLException ex ) {
             rowDeleted = 0;
         }
        return rowDeleted;
    }
    public static int insertStudent(DatabaseHandler dbHandler, Student stu) {
         String sql = "insert into student (id, name, gpa)" + 
               " values (?,?,?)";
         
         int rowInserted;
         try {
             rowInserted = dbHandler.update(sql, stu.getId(), stu.getName(), stu.getGPA());
         }
         catch(SQLException ex ) {
             rowInserted = 0;
         }
         return rowInserted;
    } 
     public static Student findStudentById(DatabaseHandler dbHandler, int id) throws SQLException {
        String sql = "select * from student where id = ?";
        ResultSet rs;
        Student stu = null;
        rs = dbHandler.query(sql, id);
        if (rs.next()) {
           stu = new Student();
           stu.setId(rs.getInt("id"));
           stu.setName(rs.getString("name"));
           stu.setGPA(rs.getDouble("gpa"));
       }
        return stu;
        
    }
    public static ArrayList<Student> findStudentByName(DatabaseHandler dbHandler, String name) throws SQLException {
        String sql = "select * from student where name = ?";
        ResultSet rs;
        ArrayList<Student> stuList = null;
        rs = dbHandler.query(sql, name);
        stuList = extractStudent(rs);
        return stuList;
        
    } 
    public static ArrayList<Student> findAllStudent(DatabaseHandler dbHandler) throws SQLException {
        String sql = "select * from student order by id";
        ResultSet rs; 
        ArrayList<Student> stuList = null;
        rs = dbHandler.query(sql);
        stuList = extractStudent(rs);
        return stuList;
    }
    private static ArrayList<Student> extractStudent(ResultSet rs) {
        ArrayList<Student> stuList = new ArrayList<>();
        try {
            while(rs.next()) {
                Student student = new Student();
                try {
                    student.setId(rs.getInt("id"));
                    student.setName(rs.getString("name"));
                    student.setGPA(rs.getDouble("gpa"));
                } catch (SQLException ex) {
                    Logger.getLogger(StudentTable.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                stuList.add(student);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(stuList.size() == 0) {
            stuList = null;
        }
        return stuList;
    }
}
