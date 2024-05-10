<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
								xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="A4" page-height="29.7cm" page-width="21.0cm"
															 margin-top="1cm" margin-bottom="1cm" margin-left="2cm" margin-right="2cm">
					<fo:region-body margin-top="3cm" margin-bottom="3cm"/>
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="A4">
				<fo:flow flow-name="xsl-region-body">
					<fo:block font-size="12pt" font-family="Roboto" text-align="left">
						<xsl:apply-templates/>
					</fo:block>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>

	<xsl:template match="newOrganizationOrder">
		<fo:block>
			<fo:block font-weight="bold">Приказ о регистрации новой организации</fo:block>
			<fo:block>Название: <xsl:value-of select="organizationInfo/name"/></fo:block>
			<fo:block>Адрес: <xsl:value-of select="organizationInfo/address"/></fo:block>
			<fo:block>Телефон: <xsl:value-of select="organizationInfo/phone"/></fo:block>
			<fo:block>Email: <xsl:value-of select="organizationInfo/email"/></fo:block>

			<fo:block font-weight="bold">Публичная информация:</fo:block>
			<fo:block>Название: <xsl:value-of select="publicInformation/name"/></fo:block>
			<fo:block>URL: <xsl:value-of select="publicInformation/url"/></fo:block>

			<fo:block font-weight="bold">Представитель организации:</fo:block>
			<fo:block>ФИО: <xsl:value-of select="organizationContactPerson/name"/></fo:block>
			<fo:block>Должность: <xsl:value-of select="organizationContactPerson/position"/></fo:block>
			<fo:block>Телефон: <xsl:value-of select="organizationContactPerson/phone"/></fo:block>
			<fo:block>Email: <xsl:value-of select="organizationContactPerson/email"/></fo:block>

			<fo:block font-weight="bold">Подписант:</fo:block>
			<fo:block>ФИО: <xsl:value-of select="signer/name"/></fo:block>
			<fo:block>Должность: <xsl:value-of select="signer/position"/></fo:block>

			<fo:block font-weight="bold">Дата подписи</fo:block>
			<fo:block><xsl:value-of select="orderDate"/></fo:block>
		</fo:block>
	</xsl:template>

</xsl:stylesheet>

