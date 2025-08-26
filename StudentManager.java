package com.Studd;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class StudentManager 
{
    private List<Student> students;
    private Path filePath;

    public StudentManager(String filename)
    {
        this.filePath = Paths.get(filename);
    
        loadFromFile();
    
    }

    // Load list from file (if exists)
    @SuppressWarnings("unchecked")
    private void loadFromFile()
    {
    
    	if (Files.exists(filePath))
    	{
        
    		try (ObjectInputStream ois = new ObjectInputStream(
            
    				new BufferedInputStream(Files.newInputStream(filePath))))
    		{
                Object obj = ois.readObject();
              
                if (obj instanceof List)
                {
                    students = (List<Student>) obj;
                } else
                {
                    students = new ArrayList<>();
                }
            }
    		catch (EOFException eof)
    		{
                students = new ArrayList<>();
            }
    		catch (Exception e)
    		{
                e.printStackTrace();
                students = new ArrayList<>();
            }
        } else
        {
            students = new ArrayList<>();
        }
    }

    // Save list to file (write to temp then replace)
    private boolean saveToFile()
    {
        Path tmp = filePath.resolveSibling(filePath.getFileName().toString() + ".tmp");
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(Files.newOutputStream(tmp,
                        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)))) {
            oos.writeObject(students);
            oos.flush();
            Files.move(tmp, filePath, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            try { Files.deleteIfExists(tmp); } catch (IOException ex) { /* ignore */ }
            return false;
        }
    }

    public boolean addStudent(Student s) {
        if (findById(s.getId()) != null) return false; // id must be unique
        students.add(s);
        return saveToFile();
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students); // return copy
    }

    public Student findById(int id) {
        for (Student s : students) if (s.getId() == id) return s;
        return null;
    }

    public boolean updateStudent(int id, String newName, Integer newAge, String newCourse) {
        Student s = findById(id);
        if (s == null) return false;
        if (newName != null) s.setName(newName);
        if (newAge != null) s.setAge(newAge);
        if (newCourse != null) s.setCourse(newCourse);
        return saveToFile();
    }

    public boolean deleteStudent(int id) {
        Iterator<Student> it = students.iterator();
        while (it.hasNext()) {
            if (it.next().getId() == id) {
                it.remove();
                return saveToFile();
            }
        }
        return false;
    }
}
