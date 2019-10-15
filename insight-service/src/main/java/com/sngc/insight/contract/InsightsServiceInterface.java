package com.sngc.insight.contract;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

public interface InsightsServiceInterface
{
    @GetMapping
    public void getInsight();

    @PostMapping
    public void createInsight();

    @PostMapping
    public void createProperties();

    @GetMapping
    public void getProperties();
}
