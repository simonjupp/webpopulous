package uk.ac.ebi.fgpt.populous.model;

import java.net.URI;

/**
 * Created by dwelter on 02/07/14.
 */
public interface EntityCreation {

    URI getDefaultBaseURI() ;


    boolean useDefaultBaseURI();


    String getDefaultSeparator();


    Class<? extends AutoIDGenerator> getAutoIDGeneratorClass();


    String getAutoIDGeneratorName();


    String getPrefix();


    String getSuffix();


    int getAutoIDDigitCount();


    int getAutoIDStart();


    int getAutoIDEnd();


    boolean isFragmentAutoGenerated();


    boolean isGenerateNameLabel();


    URI getNameLabelURI() ;


    String getNameLabelLang();


    boolean isGenerateIDLabel();


    Class<? extends LabelDescriptor> getLabelDescriptorClass() ;



}
