-- ----------------------------
-- Table structure for "C##FHADMIN"."${tabletop}${objectNameOriginal}"
-- ----------------------------
-- DROP TABLE "C##FHADMIN"."${tabletop}${objectNameOriginal}";
CREATE TABLE "C##FHADMIN"."${tabletop}${objectNameOriginal}" (
<#list fieldList as var>
	<#if var[1] == 'Integer'>
	"${var[0]}" NUMBER(${var[5]}) NULL ,
	<#elseif var[1] == 'Double'>
	"${var[0]}" NUMBER(${var[5]},${var[6]}) NULL ,
	<#else>
	"${var[0]}" VARCHAR2(${var[5]} BYTE) NULL ,
	</#if>
</#list>
	"${objectNameOriginal}_ID" VARCHAR2(100 BYTE) NOT NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;

<#list fieldList as var>
COMMENT ON COLUMN "C##FHADMIN"."${tabletop}${objectNameOriginal}"."${var[0]}" IS '${var[2]}';
</#list>
COMMENT ON COLUMN "C##FHADMIN"."${tabletop}${objectNameOriginal}"."${objectNameOriginal}_ID" IS 'ID';

-- ----------------------------
-- Indexes structure for table ${tabletop}${objectNameOriginal}
-- ----------------------------

-- ----------------------------
-- Checks structure for table "C##FHADMIN"."${tabletop}${objectNameOriginal}"

-- ----------------------------

ALTER TABLE "C##FHADMIN"."${tabletop}${objectNameFirstLower}" ADD CHECK ("${objectNameFirstLower}_ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table "C##FHADMIN"."${tabletop}${objectNameFirstLower}"
-- ----------------------------
ALTER TABLE "C##FHADMIN"."${tabletop}${objectNameFirstLower}" ADD PRIMARY KEY ("${objectNameFirstLower}_ID");
