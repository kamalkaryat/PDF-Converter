package com.ksk.image2pdfconverter.interfaces;

import com.ksk.image2pdfconverter.model.EnhancementOptionsEntity;
/**
 * The {@link Enhancer} is a functional interface for all enhancements.
 */
public interface Enhancer {
    /**
     * To apply an enhancement.
     */
    void enhance();

    /**
     * @return The {@link EnhancementOptionsEntity} for this {@link Enhancer}.
     */
    EnhancementOptionsEntity getEnhancementOptionsEntity();
}
