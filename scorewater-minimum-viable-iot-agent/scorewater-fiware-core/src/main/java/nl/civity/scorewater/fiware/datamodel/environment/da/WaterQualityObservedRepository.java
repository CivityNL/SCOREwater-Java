/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.civity.scorewater.fiware.datamodel.environment.da;

import nl.civity.scorewater.fiware.datamodel.ObservedIdentifier;
import nl.civity.scorewater.fiware.datamodel.environment.WaterQualityObserved;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author basvanmeulebrouk
 */
public interface WaterQualityObservedRepository extends JpaRepository<WaterQualityObserved, ObservedIdentifier> {
    
}
