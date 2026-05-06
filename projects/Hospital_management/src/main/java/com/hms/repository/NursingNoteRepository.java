package com.hms.repository;

import com.hms.entity.NursingNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NursingNoteRepository extends JpaRepository<NursingNote, Long> {
    List<NursingNote> findByAdmissionIdOrderByRecordedAtDesc(Long admissionId);
}
