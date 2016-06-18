Classifier
============

What is this? This is a mechansim to take Rx records and create a time-series cluster

Order of operation: 
1. common.conceptspace.PopulateConcepts.java [ common.Library.DATA_FOR_REAL -> common.Library.RIV_STORE ]


2. common.rollup.PersonRollup.java A: reads step1's output AND B: rereads the raw data that the 1st used ( but still time to aggrigate the data into a collection based on the PERSON_ID/TIME_SERIES information of the Rx data ). Then it MERGES A & B so now the collection of PERSON_ID/TIME_SERIES are somewhere in the hyper-space [ Output to Library.ROLLUP_FILE]

3. common.clustering.CreateClusters.java will read Library.ROLLUP_FILE and pass in the PERSON_ID/TIME_SERIES rollup data into the KShiftMeans clusterer. 

4. TODO: Have the KShiftMeans clusterer mashal data 

In more detail: 

1.  The begining data are a million .csv rows like this: 
person_id,gender_code,ccs_category_id,ndc_code,drug_label_name,drug_group_description,days_supply_count,filled_date,patient_paid_amount,ingredient_cost_paid_amount,after_cure,during_treatment,before_diagnosis
385177,M,29,00591352530,LIDOCAINE 5 % PATCH,DERMATOLOGICALS,-30,12/10/2014,-33,-182.53,True,False,False and creates a new file with this shape:
2. seen|feature|payload|riv
92828|ccs_category_id|22|1.0,-1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,-1.0,-1.0,-1.0,-1.0,1.0,1.0,-1.0,-1.0,1.0,-1.0,-1.0,-1.0,1.0,1.0,1.0,-1.0,1.0,1.0,-1.0,-1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,1.0,1.0,1.0,-1.0,-1.0,-1.0,1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,-1.0

Note: The second file reads the entire first file and for *unique* entries and places each unique into a hyper-space ( 50 element vector of random 1 or -1 ).

3. Create this: person_id|when|velocity|days_supply_count|patient_paid_amount|ingredient_cost_paid_amount|riv
3578197|0|7|71|21|101|15.0,5.0,-5.0,-1.0,5.0,3.0,1.0,-1.0,1.0,-3.0,1.0,-1.0,1.0,-1.0,-5.0,-3.0,3.0,1.0,9.0,1.0,-3.0,1.0,3.0,-1.0,-3.0,-7.0,-1.0,-5.0,1.0,1.0,-1.0,1.0,-7.0,5.0,-7.0,-5.0,1.0,1.0,-3.0,3.0,3.0,3.0,-5.0,1.0,5.0,-5.0,1.0,-1.0,-7.0,1.0
3578197|1|5|88|26|138|15.0,5.0,-5.0,-1.0,5.0,3.0,1.0,-1.0,1.0,-3.0,1.0,-1.0,1.0,-1.0,-5.0,-3.0,3.0,1.0,9.0,1.0,-3.0,1.0,3.0,-1.0,-3.0,-7.0,-1.0,-5.0,1.0,1.0,-1.0,1.0,-7.0,5.0,-7.0,-5.0,1.0,1.0,-3.0,3.0,3.0,3.0,-5.0,1.0,5.0,-5.0,1.0,-1.0,-7.0,1.0

4. Read step3 and pass into kmeans.ReduxShiftMeans.java
