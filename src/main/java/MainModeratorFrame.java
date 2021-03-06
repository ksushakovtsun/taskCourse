import com.springapp.mvc.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/mainmoderator")
public class MainModeratorFrame extends HttpServlet{
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int answer = 0;
        try {
            answer = checkAction(req);
        } catch (SQLException sql_e) {
            throw new IOException(sql_e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (answer == 4) {
            try {
                ManagementSystem.getInstance().logOut();
                getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        req.setAttribute("logIn", "");
        req.setAttribute("logIn2", "");
        req.setAttribute("logIn3", "");
        String ds = req.getParameter("departmentId");
        String ys = req.getParameter("year");
        int departmentId = -1;
        if (ds != null) {
            departmentId = Integer.parseInt(ds);
        }
        int year = Calendar.getInstance().get(Calendar.YEAR);
        if (ys != null) {
            year = Integer.parseInt(ys);
        }
        String gs = req.getParameter("groupId");
        int groupId = -1;
        if (gs != null) {
            groupId = Integer.parseInt(gs);
        }
        if (answer == 1) {
            try {
                Pattern patternName = Pattern.compile("^[A-Z]([a-z]{3,20})$");
                Pattern patternDate = Pattern.compile("[0-3]\\d\\.[01]\\d\\.\\d{4}");
                String sN =  (String) req.getParameter("lblL");
                String fN =  (String) req.getParameter("lblF");
                String p =  (String) req.getParameter("lblF");
                String d =  (String) req.getParameter("lblD");
                Matcher matcherSurN = patternName.matcher(sN);
                Matcher matcherFirstN = patternName.matcher(fN);
                Matcher matcherPatron = patternName.matcher(p);
                Matcher matcherDate = patternDate.matcher(d);
                boolean resultSurN = matcherSurN.matches();
                boolean resultFirstN = matcherFirstN.matches();
                boolean resultPayron = matcherPatron.matches();
                boolean resultDate = matcherDate.matches();
                if (resultSurN == true) {
                    if (resultFirstN == true) {
                        if (resultPayron == true) {
                            if (resultDate == true) {
                                String s = (String) req.getParameter("lblS");
                                String sex = s.toUpperCase();
                                Student student = new Student();
                                student.setSurName(req.getParameter("lblL"));
                                student.setFirstName(req.getParameter("lblF"));
                                student.setPatronymic(req.getParameter("lblP"));
                                student.setDateOfBirth(sdf.parse(req.getParameter("lblD")));
                                student.setSex(sex);
                                student.setGroupId(groupId);
                                student.setEducationYear(year);
                                ManagementSystem.getInstance().insertStudent(student);
                            }else {
                                req.setAttribute("logIn", "Invalid date!");
                            }
                        } else {
                            req.setAttribute("logIn", "Invalid patronomic!");
                        }
                    }else {
                        req.setAttribute("logIn", "Invalid firstname!");
                    }
                }else {
                    req.setAttribute("logIn", "Invalid lastname!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (answer == 6) {
            Pattern patternName = Pattern.compile("^[A-Z]([a-z]{3,20}|[a-z]{3,20}\\s[A-Z]([a-z]{3,20})|[a-z]{3,20}\\s[a-z]([a-z]{3,20}))$");
            Pattern patternNum = Pattern.compile("\\d{3}");
            String grN =  (String) req.getParameter("lblGrN");
            String grC =  (String) req.getParameter("lblGrC");
            String grS =  (String) req.getParameter("lblGrS");
            String grH =  (String) req.getParameter("lblGrH");
            Matcher matcherGrN = patternNum.matcher(grN);
            Matcher matcherGrC = patternName.matcher(grC);
            Matcher matcherGrS = patternName.matcher(grS);
            Matcher matcherGrH = patternName.matcher(grH);
            boolean resultGrN = matcherGrN.matches();
            boolean resultGrC = matcherGrC.matches();
            boolean resultGrS = matcherGrS.matches();
            boolean resultGrH = matcherGrH.matches();
            if (resultGrN == true) {
                if (resultGrC == true) {
                    if (resultGrS == true) {
                        if (resultGrH == true) {
                            try {
                                Group group = new Group();
                                group.setNameGroup(req.getParameter("lblGrN"));
                                group.setCurator(req.getParameter("lblGrC"));
                                group.setSpeciality(req.getParameter("lblGrS"));
                                group.setSpeHead(req.getParameter("lblGrH"));
                                group.setDepartmentId(Integer.parseInt(req.getParameter("departmentId")));
                                ManagementSystem.getInstance().insertGroup(group);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            req.setAttribute("logIn2", "Invalid head sp!");
                        }
                    }else {
                        req.setAttribute("logIn2", "Invalid specialty!");
                    }
                }else {
                    req.setAttribute("logIn2", "Invalid curator!");
                }
            }else {
                req.setAttribute("logIn2", "Invalid name!");
            }
        }
        if (answer == 10) {
            Pattern patternHead = Pattern.compile("^[A-Z]([a-z]{3,20})$");
            Pattern patternName = Pattern.compile("^[A-Z]{3}$");
            String dpN =  (String) req.getParameter("lblDpN");
            String dpH =  (String) req.getParameter("lblDpH");
            Matcher matcherDpN = patternName.matcher(dpN);
            Matcher matcherDpH = patternHead.matcher(dpH);
            boolean resultDpN = matcherDpN.matches();
            boolean resultDpH = matcherDpH.matches();
            if (resultDpN == true) {
                if (resultDpH == true) {
                    try {
                        Department department = new Department();
                        department.setNameDept(req.getParameter("lblDpN"));
                        department.setHead(req.getParameter("lblDpH"));
                        ManagementSystem.getInstance().insertDepartment(department);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    req.setAttribute("logIn3", "Invalid name!");
                }
            }else {
                req.setAttribute("logIn3", "Invalid head!");
            }
        }
        if (answer == 7) {
            if (req.getParameter("grId") != null) {
                Group group = new Group();
                group.setGroupId(Integer.parseInt(req.getParameter("grId")));
                try {
                    ManagementSystem.getInstance().deleteGroup(group);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (answer == 11) {
            if (req.getParameter("dpId") != null) {
                Department department = new Department();
                department.setDepartmentId(Integer.parseInt(req.getParameter("dpId")));
                try {
                    ManagementSystem.getInstance().deleteDepartment(department);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (answer == 3) {
            String newGs = req.getParameter("newGroupId");
            String newYs = req.getParameter("newYear");
            try {
                Group g = new Group();
                g.setGroupId(Integer.parseInt(gs));
                Group ng = new Group();
                ng.setGroupId(Integer.parseInt(newGs));
                ManagementSystem.getInstance().moveStudentsToGroup(g, Integer.parseInt(ys), ng, Integer.parseInt(newYs));
            } catch (SQLException sql_e) {
                throw new IOException(sql_e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        MainFrameForm form = new MainFrameForm();
        try {
            Collection departments = ManagementSystem.getInstance().getDepartments();
            Department d = new Department();
            d.setDepartmentId(departmentId);
            if (departmentId == -1) {
                Iterator i = departments.iterator();
                d = (Department) i.next();
            }
            Collection groups = ManagementSystem.getInstance().getGroupsFromDepartment(d);
            form.setDepartmentId(d.getDepartmentId());
            form.setDepartments(departments);
            form.setGroups(groups);
            String gs1 = req.getParameter("groupId");
            int groupId1 = -1;
            if (gs1 != null) {
                groupId1 = Integer.parseInt(gs1);
            }
            Group g = new Group();
            g.setGroupId(groupId1);
            if (groupId1 == -1) {
                Iterator i = groups.iterator();
                g = (Group) i.next();
            }
            form.setGroupId(g.getGroupId());
            form.setYear(year);
            Collection students = ManagementSystem.getInstance().getStudentsFromGroup(g, year);
            form.setStudents(students);
        } catch (SQLException sql_e) {
            throw new IOException(sql_e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        req.setAttribute("form", form);
        try {
            Collection groups = ManagementSystem.getInstance().getGroups();
            req.setAttribute("group", groups);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (answer == 5) {
            try {
                String s = (String) req.getParameter("lblS");
                String sex = s.toUpperCase();
                Student student = new Student();
                student.setSurName(req.getParameter("lblL"));
                student.setFirstName(req.getParameter("lblF"));
                student.setPatronymic(req.getParameter("lblP"));
                student.setDateOfBirth(sdf.parse(req.getParameter("lblD")));
                student.setSex(sex);
                student.setGroupId(groupId);
                student.setEducationYear(year);
                student.setStudentId(Integer.parseInt(req.getParameter("lblID")));
                ManagementSystem.getInstance().updateStudent(student);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (answer == 8) {
            try {
                Group group = new Group();
                group.setNameGroup(req.getParameter("lblGrN"));
                group.setCurator(req.getParameter("lblGrC"));
                group.setSpeciality(req.getParameter("lblGrS"));
                group.setSpeHead(req.getParameter("lblGrH"));
                group.setGroupId(Integer.parseInt(req.getParameter("lblGrID")));
                ManagementSystem.getInstance().updateGroup(group);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (answer == 12) {
            try {
                Department department = new Department();
                department.setNameDept(req.getParameter("lblDpN"));
                department.setHead(req.getParameter("lblDpH"));
                department.setDepartmentId(Integer.parseInt(req.getParameter("lblDpID")));
                ManagementSystem.getInstance().updateDepartment(department);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (answer == 2) {
            try {
                if (req.getParameter("studentId") != null) {
                    int stId = Integer.parseInt(req.getParameter("studentId"));
                    Student student = ManagementSystem.getInstance().getStudentById(stId);
                    Collection groups = ManagementSystem.getInstance().getGroups();
                    StudentForm sForm = new StudentForm();
                    sForm.initFromStudent(student);
                    sForm.setGroups(groups);
                    req.setAttribute("student", sForm);
                    getServletContext().getRequestDispatcher("/MainModeratorFrame.jsp").forward(req, resp);
                    return;
                }
            } catch (SQLException sql_e) {
                throw new IOException(sql_e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (answer == 9) {
            try {
                if (req.getParameter("grId") != null) {
                    int grId = Integer.parseInt(req.getParameter("grId"));
                    Group group = ManagementSystem.getInstance().getGroupById(grId);
                    req.setAttribute("groupE", group);
                    getServletContext().getRequestDispatcher("/MainModeratorFrame.jsp").forward(req, resp);
                    return;
                }
            } catch (SQLException sql_e) {
                throw new IOException(sql_e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (answer == 13) {
            try {
                if (req.getParameter("dpId") != null) {
                    int dpId = Integer.parseInt(req.getParameter("dpId"));
                    Department department = ManagementSystem.getInstance().getDepartmentById(dpId);
                    req.setAttribute("department", department);
                    getServletContext().getRequestDispatcher("/MainModeratorFrame.jsp").forward(req, resp);
                    return;
                }
            } catch (SQLException sql_e) {
                throw new IOException(sql_e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        getServletContext().getRequestDispatcher("/MainModeratorFrame.jsp").forward(req, resp);
    }

    private int checkAction(HttpServletRequest req) throws Exception {
        if (req.getParameter("Add") != null) {
            return 1;
        }
        if (req.getParameter("Edit") != null) {
            return 2;
        }
        if (req.getParameter("MoveGroup") != null) {
            return 3;
        }
        if (req.getParameter("logOut") != null) {
            return 4;
        }
        if (req.getParameter("EditFinaly") != null) {
            return 5;
        }
        if (req.getParameter("AddGr") != null) {
            return 6;
        }
        if (req.getParameter("DeleteGr") != null) {
            return 7;
        }
        if (req.getParameter("EditFinalyGr") != null) {
            return 8;
        }
        if (req.getParameter("EditGr") != null) {
            return 9;
        }
        if (req.getParameter("AddDp") != null) {
            return 10;
        }
        if (req.getParameter("DeleteDp") != null) {
            return 11;
        }
        if (req.getParameter("EditFinalyDp") != null) {
            return 12;
        }
        if (req.getParameter("EditDp") != null) {
            return 13;
        }
        if (req.getParameter("Delete") != null) {
            if (req.getParameter("studentId") != null) {
                Student student = new Student();
                student.setStudentId(Integer.parseInt(req.getParameter("studentId")));
                ManagementSystem.getInstance().deleteStudent(student);
            }
            return 0;
        }
        return 0;
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

}
