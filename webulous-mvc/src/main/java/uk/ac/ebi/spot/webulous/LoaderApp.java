package uk.ac.ebi.spot.webulous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uk.ac.ebi.spot.webulous.model.PopulousDataRestriction;
import uk.ac.ebi.spot.webulous.model.PopulousPattern;
import uk.ac.ebi.spot.webulous.model.PopulousTemplateDocument;
import uk.ac.ebi.spot.webulous.model.RestrictionType;
import uk.ac.ebi.spot.webulous.repository.DataConversionRunRepository;
import uk.ac.ebi.spot.webulous.repository.PopulousTemplateRepository;
import uk.ac.ebi.spot.webulous.repository.RestrictionRunRepository;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Simon Jupp
 * @date 17/03/2015
 * Samples, Phenotypes and Ontologies Team, EMBL-EBI
 */
@SpringBootApplication
public class LoaderApp  implements CommandLineRunner {

    @Autowired
    private PopulousTemplateRepository templateRepository;
    @Autowired
    private DataConversionRunRepository dataRunRepository;
    @Autowired
    private RestrictionRunRepository restrictionRunRepository;

    @Override
    public void run(String... strings) throws Exception {

        System.out.println("creating a new teamplate");
        PopulousTemplateDocument document = new PopulousTemplateDocument();
        document.setDescription("Pizza template");
        document.setActive(true);
        document.setAdminEmailAddresses("jupp@ebi.ac.uk");
        document.setActiveOntology("http://www.test.com/pizza.owl");
        Set<String> ontologyImports= new HashSet<String>();
        URL ontologyURL = getClass().getClassLoader().getResource("pizza.owl");
        ontologyImports.add("file:" + ontologyURL.getFile());
//        ontologyImports.add("file:/Users/jupp/Dropbox/dev/ols/ontology-tools/src/test/resources/test-import-2.owl");

        document.setOntologyImports(ontologyImports);
//        document.setLastUpdated(new Date());
//        document.setStatus(Status.OK);



        PopulousDataRestriction dr1 = new PopulousDataRestriction(1, "Pizza");
//        dr1.setClassExpression("A");
        dr1.setVariableName("?pizza");
        dr1.setRestrictionType(RestrictionType.UNRESTRICTED);

        PopulousDataRestriction dr2 = new PopulousDataRestriction(2, "Fish toppings");
        dr2.setVariableName("?fishTopping");
        dr2.setRestrictionType(RestrictionType.DESCENDANTS);
        dr2.setClassExpression("SeafoodTopping");
        String[][] fishValues = new String[2][2];
        fishValues[0][0] = "AnchovyTopping";
        fishValues[0][1] = "http://www.pizza.com/ontologies/pizza.owl#AnchovyTopping";
        fishValues[1][0] = "PrawnTopping";
        fishValues[1][1] = "http://www.pizza.com/ontologies/pizza.owl#PrawnTopping";
        dr2.setValues(fishValues);

        PopulousDataRestriction dr3 = new PopulousDataRestriction(3, "Meat toppings");
        dr3.setVariableName("?meatTopping");
        dr3.setClassExpression("MeatTopping");
        dr3.setRestrictionType(RestrictionType.DESCENDANTS);
        String[][] meatValues = new String[2][2];
        meatValues[0][0] = "HamTopping";
        meatValues[0][1] = "http://www.pizza.com/ontologies/pizza.owl#HamTopping";
        meatValues[1][0] = "SalamiTopping";
        meatValues[1][1] = "http://www.pizza.com/ontologies/pizza.owl#SalamiTopping";
        dr3.setValues(meatValues);

        List<PopulousDataRestriction> restrictionSet = new ArrayList<PopulousDataRestriction>();
        restrictionSet.add(dr1);
        restrictionSet.add(dr2);
        restrictionSet.add(dr3);
        document.setDataRestrictions(restrictionSet);

        PopulousPattern populousPattern1 = new PopulousPattern();
        populousPattern1.setPatternName("Meat topping pattern");
        populousPattern1.setPatternValue("?pizza:CLASS,\n?meatTopping:CLASS\nBEGIN\nADD ?pizza subClassOf hasTopping some ?meatTopping\nEND;\n");

        PopulousPattern populousPattern2 = new PopulousPattern();
        populousPattern2.setPatternName("Fish topping pattern");
        populousPattern2.setPatternValue("?pizza:CLASS,\n?fishTopping:CLASS\nBEGIN\nADD ?pizza subClassOf hasTopping some ?fishTopping\nEND;\n");

        List<PopulousPattern> patterns = new ArrayList<PopulousPattern>();
        patterns.add(populousPattern1);
        patterns.add(populousPattern2);
        document.setPatterns(patterns);

        //        Map<String, Integer> variableToColumnMap = new HashMap<String, Integer>();
        //        variableToColumnMap.put("?a", 1);
        //        variableToColumnMap.put("?b", 2);
        //        document.setVariableToColumnMap(variableToColumnMap);

        System.out.println("template created about to save");

        restrictionRunRepository.deleteAll();
        dataRunRepository.deleteAll();
//        templateRepository.deleteAll();
//        templateRepository.save(document);

        System.out.println("saved!");




    }
    public static void main(String[] args) throws Exception {
        SpringApplication.run(LoaderApp.class, args);
    }
}