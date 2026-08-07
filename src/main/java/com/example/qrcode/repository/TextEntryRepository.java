package com.example.qrcode.repository;

import com.example.qrcode.entity.TextEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextEntryRepository extends JpaRepository<TextEntry, Long> {
}
