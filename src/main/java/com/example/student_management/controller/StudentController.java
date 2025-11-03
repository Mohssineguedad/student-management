package com.example.student_management.controller;

import com.example.student_management.entity.Student;
import com.example.student_management.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/students")
@Tag(name = "Student Management", description = "Endpoints for managing students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Operation(
            summary = "Créer ou modifier un étudiant",
            description = "Crée un nouvel étudiant ou modifie un étudiant existant dans la base de données"
    )
    @ApiResponse(responseCode = "201", description = "Étudiant créé ou mis à jour avec succès")
    @PostMapping("/save")
    public ResponseEntity<Student> save(@RequestBody Student student) {
        Student savedStudent = studentService.save(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Supprimer un étudiant par ID",
            description = "Supprime un étudiant existant en fonction de son identifiant unique"
    )
    @ApiResponse(responseCode = "204", description = "Étudiant supprimé avec succès")
    @ApiResponse(responseCode = "404", description = "Étudiant non trouvé")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        boolean deleted = studentService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Récupérer tous les étudiants",
            description = "Retourne la liste complète de tous les étudiants enregistrés dans la base de données"
    )
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    @GetMapping("/all")
    public ResponseEntity<List<Student>> findAll() {
        List<Student> students = studentService.findAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @Operation(
            summary = "Compter le nombre total d’étudiants",
            description = "Retourne le nombre total d’étudiants enregistrés dans la base de données"
    )
    @ApiResponse(responseCode = "200", description = "Nombre total retourné avec succès")
    @GetMapping("/count")
    public ResponseEntity<Long> countStudent() {
        long count = studentService.countStudents();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @Operation(
            summary = "Compter les étudiants par année de naissance",
            description = "Retourne une collection indiquant le nombre d’étudiants regroupés par année de naissance"
    )
    @ApiResponse(responseCode = "200", description = "Statistiques récupérées avec succès")
    @GetMapping("/byYear")
    public ResponseEntity<Collection<?>> findByYear() {
        Collection<?> studentsByYear = studentService.findNbrStudentByYear();
        return new ResponseEntity<>(studentsByYear, HttpStatus.OK);
    }
}
