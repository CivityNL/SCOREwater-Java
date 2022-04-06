/*
 * Copyright (c) 2021, Civity BV Zeist
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * * Neither the name of the copyright holder nor the names of its contributors 
 *   may be used to endorse or promote products derived from this software without 
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package nl.civity.scorewater.fiware.datamodel.environment;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Arrays;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import nl.civity.scorewater.fiware.datamodel.Location;
import nl.civity.scorewater.fiware.datamodel.Observed;

@Entity
public class WaterQualityObserved extends Observed implements Serializable {

    // Temperature.
    // - Attribute type: Property. Number
    // - Attribute metadata:
    //   - timestamp: Timestamp when the last update of the attribute happened.
    //   - Type: DateTime
    // - Default unit: Celsius Degrees.
    // - Optional
    private Double temperature;
    private ZonedDateTime temperatureTimestamp;

    // Electrical Conductivity.
    // - Attribute type: Property. Number
    // - Attribute metadata:
    //   - timestamp: Timestamp when the last update of the attribute happened.
    //     - Type: DateTime
    // - Default unit: Siemens per meter (S/m).
    // - Optional
    private Double conductivity;
    private ZonedDateTime conductivityTimestamp;

    // Specific Conductance.
    // - Attribute type: Property. Number
    // - Attribute metadata:
    //   - timestamp: Timestamp when the last update of the attribute happened.
    //     - Type: DateTime
    // - Default unit: Siemens per meter at 25 ºC (S/m).
    // - Optional
    private Double conductance;
    private ZonedDateTime conductanceTimestamp;

    // Total suspended solids.
    // - Attribute type: Property. Number
    // - Attribute metadata:
    //   - timestamp: Timestamp when the last update of the attribute happened.
    //     - Type: DateTime
    // - Default unit: milligrams per liter (mg/L).
    // - Optional
    private Double tss;
    private ZonedDateTime tssTimestamp;

    // Total dissolved solids.
    // - Attribute type: Property. Number
    // - Attribute metadata:
    //   - timestamp: Timestamp when the last update of the attribute happened.
    //     - Type: DateTime
    // - Default unit: milligrams per liter (mg/L).
    // - Optional
    private Double tds;
    private ZonedDateTime tdsTimestamp;

    // Amount of light scattered by particles in the water column.
    // - Attribute type: Property. Number
    // - Attribute metadata:
    //   - timestamp: Timestamp when the last update of the attribute happened.
    //     - Type: DateTime
    // - Default unit: Formazin Turbidity Unit (FTU).
    // - Optional
    private Double turbidity;
    private ZonedDateTime turbidityTimestamp;

    // Amount of salts dissolved in water.
    // - Attribute type: Property. Number
    // - Attribute metadata:
    //   - timestamp: Timestamp when the last update of the attribute happened.
    //     - Type: DateTime
    // - Default unit: Parts per thousand (ppt).
    // - Optional
    private Double salinity;
    private ZonedDateTime salinityTimestamp;

    // Acidity or basicity of an aqueous solution.
    // - Attribute type: Property. Number
    // - Attribute metadata:
    //   - timestamp: Timestamp when the last update of the attribute happened.
    //     - Type: DateTime
    // - Default unit: Negative of the logarithm to base 10 of the activity of the hydrogen ion.
    // - Optional
    private Double ph;
    private ZonedDateTime phTimestamp;

    // Oxidation-Reduction potential.
    // - Attribute type: Property. Number
    // - Attribute metadata:
    //   - timestamp: Timestamp when the last update of the attribute happened.
    //     - Type: DateTime
    // - Default unit: millivolts (mV).
    // - Optional
    private Double orp;
    private ZonedDateTime orpTimestamp;

    // An array of strings containing details (see format below) about extra measurands provided by this observation.
    // - Attribute type: Property. List of Text.
    // - Allowed values: Each element of the array must be a string with the following format (comma separated list of values): <measurand>, <observedValue>, <unitcode>, <description>, where:
    //   - measurand : corresponds to the chemical formula (or mnemonic) of the measurand, ex. CO.
    //   - observedValue : corresponds to the value for the measurand as a number.
    //   - unitCode : The unit code (text) of measurement given using the UN/CEFACT Common Code (max. 3 characters). For instance, M1 represents milligrams per liter.
    //   - description : short description of the measurand.
    //   - Examples: "NO3,0.01, M1, Nitrates"
    // - Optional
    String[] measurand;

    // Level of free, non-compound oxygen present.
    // - Attribute type: Property. Number
    // - Attribute metadata:
    //   - timestamp: Timestamp when the last update of the attribute happened.
    //     - Type: DateTime
    // - Default unit: milligrams per liter (mg/L).
    // - Optional
    private Double o2;
    @Column(name = "o2_timestamp")
    private ZonedDateTime o2Timestamp;

    // Chla : Concentration of chlorophyll A.
    // - Attribute type: Property. Number
    // - Attribute metadata:
    //   - timestamp: Timestamp when the last update of the attribute happened.
    //     - Type: DateTime
    // - Default unit: micrograms per liter.
    // - Optional
    private Double chla;
    private ZonedDateTime chlaTimestamp;

    // PE : Concentration of pigment phycoerythrin which can be measured to estimate cyanobacteria concentrations specifically.
    // - Attribute type: Property. Number
    // - Attribute metadata:
    //   - timestamp: Timestamp when the last update of the attribute happened.
    //     - Type: DateTime
    // - Default unit: micrograms per liter.
    // - Optional
    private Double pe;
    private ZonedDateTime peTimestamp;

    // Concentration of pigment phycocyanin which can be measured to estimate cyanobacteria concentrations specifically.
    // - Attribute type: Property. Number
    // - Attribute metadata:
    //   - timestamp: Timestamp when the last update of the attribute happened.
    //     - Type: DateTime
    // - Default unit: micrograms per liter.
    // - Optional
    private Double pc;
    private ZonedDateTime pcTimestamp;

    // Concentration of ammonium.
    // - Attribute type: Property. Number
    // - Attribute metadata:
    //   - timestamp: Timestamp when the last update of the attribute happened.
    //     - Type: DateTime
    // - Default unit: milligrams per liter (mg/L).
    // - Optional
    private Double nh4;
    @Column(name = "nh4_timestamp")
    private ZonedDateTime nh4Timestamp;

    // Concentration of ammonia.
    // - Attribute type: Property. Number
    // - Attribute metadata:
    //   - timestamp: Timestamp when the last update of the attribute happened.
    //     - Type: DateTime
    // - Default unit: milligrams per liter (mg/L).
    // - Optional
    private Double nh3;
    @Column(name = "nh3_timestamp")
    private ZonedDateTime nh3Timestamp;

    // Concentration of chlorides.
    // - Attribute type: Property. Number
    // - Attribute metadata:
    //   - timestamp: Timestamp when the last update of the attribute happened.
    //     - Type: DateTime
    // - Default unit: milligrams per liter (mg/L).
    // - Optional
    private Double cl;
    private ZonedDateTime clTimestamp;

    // Concentration of nitrates.
    // - Attribute type: Property. Number
    // - Attribute metadata:
    //   - timestamp: Timestamp when the last update of the attribute happened.
    //     - Type: DateTime
    // - Default unit: milligrams per liter (mg/L).
    // - Optional
    private Double no3;
    @Column(name = "no3_timestamp")
    private ZonedDateTime no3Timestamp;

    // To convert NO3-N to NO3, multiply by 4.427, to convert NO3 to NO3-N devide by 4.427
    @Transient
    private final Double no3NToNo3Multiplier = 4.427; // source: https://support.hach.com/app/answers/answer_view/a_id/1000316/~/what-is-the-factor-to-convert-from-no3-n-and-no3%3F-

    public WaterQualityObserved() {
        super();
    }

    public WaterQualityObserved(String entityId, ZonedDateTime dateObserved, String dataProvider, Location location, String address) {
        super(entityId, dateObserved, "WaterQualityObserved", dataProvider, location, address);
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        setDateModified();
        this.temperature = temperature;
    }

    public void setTemperature(Double temperature, ZonedDateTime temperatureTimestamp) {
        setDateModified();
        this.temperature = temperature;
        this.temperatureTimestamp = temperatureTimestamp;
    }

    public ZonedDateTime getTemperatureTimestamp() {
        return temperatureTimestamp;
    }

    public void setTemperatureTimestamp(ZonedDateTime temperatureTimestamp) {
        setDateModified();
        this.temperatureTimestamp = temperatureTimestamp;
    }

    public Double getConductivity() {
        return conductivity;
    }

    public void setConductivity(Double conductivity) {
        setDateModified();
        this.conductivity = conductivity;
    }

    public void setConductivity(Double conductivity, ZonedDateTime conductivityTimestamp) {
        setDateModified();
        this.conductivity = conductivity;
        this.conductivityTimestamp = conductivityTimestamp;
    }

    public ZonedDateTime getConductivityTimestamp() {
        return conductivityTimestamp;
    }

    public void setConductivityTimestamp(ZonedDateTime conductivityTimestamp) {
        setDateModified();
        this.conductivityTimestamp = conductivityTimestamp;
    }

    public Double getConductance() {
        return conductance;
    }

    public void setConductance(Double conductance) {
        setDateModified();
        this.conductance = conductance;
    }

    public void setConductance(Double conductance, ZonedDateTime conductanceTimestamp) {
        setDateModified();
        this.conductance = conductance;
        this.conductanceTimestamp = conductanceTimestamp;
    }

    public ZonedDateTime getConductanceTimestamp() {
        return conductanceTimestamp;
    }

    public void setConductanceTimestamp(ZonedDateTime conductanceTimestamp) {
        setDateModified();
        this.conductanceTimestamp = conductanceTimestamp;
    }

    public Double getTss() {
        return tss;
    }

    public void setTss(Double tss) {
        setDateModified();
        this.tss = tss;
    }

    public void setTss(Double tss, ZonedDateTime tssTimestamp) {
        setDateModified();
        this.tss = tss;
        this.tssTimestamp = tssTimestamp;
    }

    public ZonedDateTime getTssTimestamp() {
        return tssTimestamp;
    }

    public void setTssTimestamp(ZonedDateTime tssTimestamp) {
        setDateModified();
        this.tssTimestamp = tssTimestamp;
    }

    public Double getTds() {
        return tds;
    }

    public void setTds(Double tds) {
        setDateModified();
        this.tds = tds;
    }

    public void setTds(Double tds, ZonedDateTime tdsTimestamp) {
        setDateModified();
        this.tds = tds;
        this.tdsTimestamp = tdsTimestamp;
    }

    public ZonedDateTime getTdsTimestamp() {
        return tdsTimestamp;
    }

    public void setTdsTimestamp(ZonedDateTime tdsTimestamp) {
        setDateModified();
        this.tdsTimestamp = tdsTimestamp;
    }

    public Double getTurbidity() {
        return turbidity;
    }

    public void setTurbidity(Double turbidity) {
        setDateModified();
        this.turbidity = turbidity;
    }

    public void setTurbidity(Double turbidity, ZonedDateTime turbidityZonedDateTime) {
        setDateModified();
        this.turbidity = turbidity;
        this.turbidityTimestamp = turbidityZonedDateTime;
    }

    public ZonedDateTime getTurbidityTimestamp() {
        return turbidityTimestamp;
    }

    public void setTurbidityTimestamp(ZonedDateTime turbidityTimestamp) {
        setDateModified();
        this.turbidityTimestamp = turbidityTimestamp;
    }

    public Double getSalinity() {
        return salinity;
    }

    public void setSalinity(Double salinity) {
        setDateModified();
        this.salinity = salinity;
    }

    public void setSalinity(Double salinity, ZonedDateTime salinityZonedDateTime) {
        setDateModified();
        this.salinity = salinity;
        this.salinityTimestamp = salinityZonedDateTime;
    }

    public ZonedDateTime getSalinityTimestamp() {
        return salinityTimestamp;
    }

    public void setSalinityTimestamp(ZonedDateTime salinityTimestamp) {
        setDateModified();
        this.salinityTimestamp = salinityTimestamp;
    }

    public Double getPh() {
        return ph;
    }

    public void setPh(Double ph) {
        setDateModified();
        this.ph = ph;
    }

    public void setpH(Double pH, ZonedDateTime pHZonedDateTime) {
        setDateModified();
        this.ph = pH;
        this.phTimestamp = pHZonedDateTime;
    }

    public ZonedDateTime getPhTimestamp() {
        return phTimestamp;
    }

    public void setPhTimestamp(ZonedDateTime phTimestamp) {
        setDateModified();
        this.phTimestamp = phTimestamp;
    }

    public Double getOrp() {
        return orp;
    }

    public void setOrp(Double orp) {
        setDateModified();
        this.orp = orp;
    }

    public void setOrp(Double orp, ZonedDateTime orpZonedDateTime) {
        setDateModified();
        this.orp = orp;
        this.orpTimestamp = orpZonedDateTime;
    }

    public ZonedDateTime getOrpTimestamp() {
        return orpTimestamp;
    }

    public void setOrpTimestamp(ZonedDateTime orpTimestamp) {
        setDateModified();
        this.orpTimestamp = orpTimestamp;
    }

    public String[] getMeasurand() {
        return measurand;
    }

    public void setMeasurand(String[] measurand) {
        setDateModified();
        this.measurand = measurand;
    }

    public Double getO2() {
        return o2;
    }

    public void setO2(Double o2) {
        setDateModified();
        this.o2 = o2;
    }

    public void setO2(Double o2, ZonedDateTime o2Timestamp) {
        setDateModified();
        this.o2 = o2;
        this.o2Timestamp = o2Timestamp;
    }

    public ZonedDateTime getO2Timestamp() {
        return o2Timestamp;
    }

    public void setO2Timestamp(ZonedDateTime o2Timestamp) {
        setDateModified();
        this.o2Timestamp = o2Timestamp;
    }

    public Double getChla() {
        return chla;
    }

    public void setChla(Double chla) {
        setDateModified();
        this.chla = chla;
    }

    public void setChla(Double chla, ZonedDateTime chlaTimestamp) {
        setDateModified();
        this.chla = chla;
        this.chlaTimestamp = chlaTimestamp;
    }

    public ZonedDateTime getChlaTimestamp() {
        return chlaTimestamp;
    }

    public void setChlaTimestamp(ZonedDateTime chlaTimestamp) {
        setDateModified();
        this.chlaTimestamp = chlaTimestamp;
    }

    public Double getPE() {
        return pe;
    }

    public void setPE(Double PE) {
        setDateModified();
        this.pe = PE;
    }

    public void setPE(Double PE, ZonedDateTime PETimestamp) {
        setDateModified();
        this.pe = PE;
        this.peTimestamp = PETimestamp;
    }

    public ZonedDateTime getPETimestamp() {
        return peTimestamp;
    }

    public void setPETimestamp(ZonedDateTime PETimestamp) {
        setDateModified();
        this.peTimestamp = PETimestamp;
    }

    public Double getPC() {
        return pc;
    }

    public void setPC(Double PC) {
        setDateModified();
        this.pc = PC;
    }

    public void setPC(Double PC, ZonedDateTime PCTimestamp) {
        setDateModified();
        this.pc = PC;
        this.pcTimestamp = PCTimestamp;
    }

    public ZonedDateTime getPCTimestamp() {
        return pcTimestamp;
    }

    public void setPCTimestamp(ZonedDateTime PCTimestamp) {
        setDateModified();
        this.pcTimestamp = PCTimestamp;
    }

    public Double getNH4() {
        return nh4;
    }

    public void setNH4(Double NH4) {
        setDateModified();
        this.nh4 = NH4;
    }

    public void setNH4(Double NH4, ZonedDateTime NH4Timestamp) {
        setDateModified();
        this.nh4 = NH4;
        this.nh4Timestamp = NH4Timestamp;
    }

    public ZonedDateTime getNH4Timestamp() {
        return nh4Timestamp;
    }

    public void setNH4Timestamp(ZonedDateTime NH4Timestamp) {
        setDateModified();
        this.nh4Timestamp = NH4Timestamp;
    }

    public Double getNH3() {
        return nh3;
    }

    public void setNH3(Double NH3) {
        setDateModified();
        this.nh3 = NH3;
    }

    public void setNH3(Double NH3, ZonedDateTime NH3Timestamp) {
        setDateModified();
        this.nh3 = NH3;
        this.nh3Timestamp = NH3Timestamp;
    }

    public ZonedDateTime getNH3Timestamp() {
        return nh3Timestamp;
    }

    public void setNH3Timestamp(ZonedDateTime NH3Timestamp) {
        setDateModified();
        this.nh3Timestamp = NH3Timestamp;
    }

    public Double getCl() {
        return cl;
    }

    public void setCl(Double cl) {
        setDateModified();
        this.cl = cl;
    }

    public void setCl(Double cl, ZonedDateTime clTimestamp) {
        setDateModified();
        this.cl = cl;
        this.clTimestamp = clTimestamp;
    }

    public ZonedDateTime getClTimestamp() {
        return clTimestamp;
    }

    public void setClTimestamp(ZonedDateTime clTimestamp) {
        setDateModified();
        this.clTimestamp = clTimestamp;
    }

    public Double getNO3() {
        return no3;
    }

    public void setNO3(Double NO3) {
        setDateModified();
        this.no3 = NO3;
    }

    public void setNO3(Double NO3, ZonedDateTime NO3Timestamp) {
        setDateModified();
        this.no3 = NO3;
        this.no3Timestamp = NO3Timestamp;
    }

    public ZonedDateTime getNO3Timestamp() {
        return no3Timestamp;
    }

    public void setNO3Timestamp(ZonedDateTime NO3Timestamp) {
        setDateModified();
        this.no3Timestamp = NO3Timestamp;
    }

    public Double getNO3NtoNO3Multiplier() {
        return no3NToNo3Multiplier;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append(this.getType()).append("{").append("temperature=").append(temperature).append(", temperatureTimestamp=").append(temperatureTimestamp).append(", conductivity=").append(conductivity).append(", conductivityTimestamp=").append(conductivityTimestamp).append(", conductance=").append(conductance).append(", conductanceTimestamp=").append(conductanceTimestamp).append(", tss=").append(tss).append(", tssTimestamp=").append(tssTimestamp).append(", tds=").append(tds).append(", tdsTimestamp=").append(tdsTimestamp).append(", turbidity=").append(turbidity).append(", turbidityZonedDateTime=").append(turbidityTimestamp).append(", salinity=").append(salinity).append(", salinityZonedDateTime=").append(salinityTimestamp).append(", pH=").append(ph).append(", pHZonedDateTime=").append(phTimestamp).append(", orp=").append(orp).append(", orpZonedDateTime=").append(orpTimestamp).append(", measurand=").append(Arrays.toString(measurand)).append(", O2=").append(o2).append(", O2Timestamp=").append(o2Timestamp).append(", Chla=").append(chla).append(", ChlaTimestamp=").append(chlaTimestamp).append(", PE=").append(pe).append(", PETimestamp=").append(peTimestamp).append(", PC=").append(pc).append(", PCTimestamp=").append(pcTimestamp).append(", NH4=").append(nh4).append(", NH4Timestamp=").append(nh4Timestamp).append(", NH3=").append(nh3).append(", NH3Timestamp=").append(nh3Timestamp).append(", Cl=").append(cl).append(", ClTimestamp=").append(clTimestamp).append(", NO3=").append(no3).append(", NO3Timestamp=").append(no3Timestamp).append(", id='").append(this.getPrimaryKey().getEntityId()).append("', type=").append(type).append(", dataProvider='").append(dataProvider).append("', dateModified=").append(dateModified).append(", dateCreated=").append(dateCreated).append(", location=").append(location).append(", address='").append(address).append("', refPointOfInterest='").append(refPointOfInterest).append("', dateObserved=").append(this.getPrimaryKey().getRecordingTimestamp()).append(", source='").append(source).append("', dateTimeFormatter=").append(dateTimeFormatter).append('}');

        return result.toString();
    }
}
