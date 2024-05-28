package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/trainings")
public class TrainingController {

  @Autowired
  private TrainingServiceImpl trainingService;

  @GetMapping
  public ResponseEntity<List<Training>> getAllTrainings() {
    return ResponseEntity.ok(trainingService.getAllTrainings());
  }

  @GetMapping("/{userId}")
  public ResponseEntity<List<Training>> getAllTrainingsForUser(@PathVariable Long userId) {
    return ResponseEntity.ok(trainingService.getAllTrainingsForUser(userId));
  }

  @GetMapping("/finished/{afterTime}")
  public ResponseEntity<List<Training>> getAllFinishedTrainingsAfter(@PathVariable String afterTime) {
    LocalDateTime afterDateTime = LocalDate.parse(afterTime).atStartOfDay();
    return ResponseEntity.ok(trainingService.getAllFinishedTrainingsAfter(afterDateTime));
  }

  @GetMapping("/activityType")
  public ResponseEntity<List<Training>> getAllTrainingByActivityType(@RequestParam ActivityType activityType) {
    return ResponseEntity.ok(trainingService.getAllTrainingsByActivityType(activityType));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Training createTraining(@RequestBody TrainingRequest trainingRequest) {
    return trainingService.createTraining(trainingRequest);
  }

  @PutMapping("/{trainingId}")
  public ResponseEntity<Training> updateTraining(@PathVariable Long trainingId, @RequestBody Training training) {
    Training updatedTraining = trainingService.updateTraining(trainingId, training);
    return ResponseEntity.ok(updatedTraining);
  }
}

