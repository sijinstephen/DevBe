<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Java Techie Mail</title>

    <style>

.preview-table {
  width: 100%;
  border: solid white;
  border-collapse: collapse;
}



.preview-tr {
  padding: 8px;
  border: 1px solid black;
}

.preview-th-td {
  text-align: center;
  border:1px solid ;
  padding: 8px;
}

.preview-footer-table {
  width: 123%;
  border:  solid black;
  border-collapse: collapse;
}

.preview-footer-tr {
  padding: 8px;
  border: 1px solid black;
}

.preview-footer-th-td {
  text-align: left;
  border: 1px solid black;
  padding: 8px;
}

.imgStyle {
  padding-left: 10px;
  padding-right: 705px;
}



    </style>
  </head>

  <body>
   


    <div>
      <div>
    
           <div class="imgStyle" style="padding-top: -100px;">
              <img
               <#-- src="C:\Users\SHERIN\git\JavaAccounts\Intuisyz_Accounts_Java\src\main\resources\image\${templateLogo}" -->
                
                src="\images/${templateLogo}"
                alt="Loading..."
                style="width: '80%'"
              />
            </div>
        
      
       <div style="padding-top: -110px;">
       
        <div style="text-align:center">
          <p style="font-size: 18 ;font-weight:bold;">
            <b>INVOICE</b>
          </p>
        </div>

        <div style="text-align:left">
         
          <p
            style="
              padding-left: 60;
              font-weight: bold;
              font-size: 15
            "
          >
            Date : ${invDate}
          </p>
        </div>
        <div style="text-align: right">
          <p
            style=" padding-right: 60; font-weight: 'bold'; font-size: 15"
          >
            Invoice No: ${invNo}
          </p>
         
          <p
            style="padding-right: 60; font-weight: bold; font-size: 15 "
          >
            GSTIN: ${gstNo}
          </p>
        
          <p
            style="padding-right: 60; font-weight: bold; font-size: 15 "
          >
            SAC Code: ${hsn}
          </p>
        </div>

        <div style="text-align: left">
          
          <p
            style="
              padding-left: 60;
              font-weight: bold;
              font-size: 15
            "
          >
            Bill To
          </p>
         

          <p
            style="
              padding-left: 60;
              font-size: 15
            "
          >
            ${billAddress}
          </p>
        </div>
        <div style="text-align: left ">
        
          <p
            style="
              padding-left: 60;
              font-weight: bold;
              font-size: 15
            "
          >
            Pay To
          </p>
         

          <p
            style="
              padding-left: 60;
              font-size: 15
            "
          >
          
       <#list templateData as item>
          ${item}
          <br/>
       </#list>
          
          </p>
        </div>
       
        <div style="padding-left: 60; padding-right: 60 ">
          <table class="preview-table">
            <tr   style="background-color:#969390; color: white; padding: 8px;  border-style: solid; border-color: white; ">
              <th style="padding: 8px; border:1px solid ;  border-color : white; text-align: center; "> &nbsp;</th>
              <th style="padding: 8px; border:1px solid ; border-color : white; text-align: center; ">Sl No</th>
              <th style="padding: 8px; border:1px solid ; border-color : white; text-align: center; ">Description</th>
              <th style="padding: 8px; border:1px solid ; border-color : white; text-align: center; ">Qty</th>
              <th style="padding: 8px; border:1px solid ; border-color : white; text-align: center; "> Amount</th>
              <!-- {/* <th class="preview-th-td">{'     bjhjghjgj   '}</th> */} -->
            </tr>

           <#list invoiceSubData as item>
                <tr class="preview-tr">
                
                <td
                    class="preview-th-td"
                    style="background-color: #969390 ; border-color : white;"> &nbsp;
                </td>
                
                  <td class="preview-th-td">${item?index+1}</td>
                  <td class="preview-th-td">${item.description}</td>
                  <td class="preview-th-td">${item.qty}</td>
                  <td class="preview-th-td">${item.amount}</td>
                  <!-- {/* <td class="preview-th-td">{'        '}</td> */} -->
                </tr>
           </#list>

            <tr class="preview-tr">
              <td
                      class="preview-th-td"
                      style=" background-color: #969390; border-color: white; "
                    > &nbsp; </td>
                    
                    
              <td class="preview-th-td"></td>
              <td class="preview-th-td">
              
                <b>GST</b>
                <br />
                <#if place_of_supply == 'InterState'>
                  <b>
                    IGST (
                    ${igstAmnt}
                    %)
                  </b>
                </#if>
              
                <#if place_of_supply == 'IntraState'>
              
                    <b>
                      CGST (
                     ${cgstAmnt}
                      
                      %)
                    </b>
                    <br />
                    <b>
                      SGST (
                      ${sgstAmnt}
                     
                      %)
                    </b>
                    
                 </#if>
              </td>
              <td class="preview-th-td"></td>
              <td class="preview-th-td">
              
                 <#if place_of_supply == 'InterState'>
                    <br />
                    <b> ${totalTaxAmnt} </b>
                 </#if>
                 
                 <#if place_of_supply == 'IntraState'>
                    <br />
                    <b> ${totalTaxAmnt} </b>
                    <br />
                    <b> ${totalTaxAmnt} </b>
                 </#if>
              </td>
              <!-- {/* <td className="preview-th-td">{'        '}</td> */} -->
            </tr>

            <tr class="preview-tr">
              <td
                      class="preview-th-td"
                      style="background-color: #969390; border-color: white;"
                    > &nbsp;</td>
              <td class="preview-th-td"></td>
                    
              <td class="preview-th-td">
                <h4>
                  
                  <b>Total</b>
                </h4>
              </td>
              <td class="preview-th-td"></td>
              <td class="preview-th-td">
                <h4>
                 
                  <b>
                   ${totalAmnt}
                  </b>
                </h4>
              </td>
              <!-- {/* <td class="preview-th-td">{'        '}</td> */} -->
            </tr>
          </table>
          <br />
         

          <div style=" padding-right: 110 ">
            <table class="preview-footer-table">
              <tr class="preview-footer-tr">
                <th class="preview-footer-th-td" style=" width: 25%; ">
                  Name <br /><br />
                  <#list templateName as item>
                    ${item}
                    <br/>
      			  </#list>
                </th>
                <th class="preview-footer-th-td" style=" width: 25%; ">
                  Signature <br /><br />
                <img
                <#--src="C:\Users\SHERIN\git\JavaAccounts\Intuisyz_Accounts_Java\src\main\resources\image\${templateSign}"-->
                src="\images/${templateSign}"
                alt="Loading..."
                style="width: 90%"
                />
                </th>
                <th class="preview-footer-th-td" style=" width: 25%; ">
                  Date
                  <br />
                  <br />
                  
                   ${invDate}
                   <br />
                  <br />
                  <br />
                </th>
              </tr>
            </table>
          </div>
          <br></br>
        </div>
      
        <div style="text-align:center; ">
         
         <p style=" font-size: 16 ">
                 
                 <#list templateCompanyName as item>
                    ${item}
                    
      			  </#list>
                 
          </p>
          
          <div>
                 <p style=" font-size: 14 ; font-family: -moz-initial ; ">
                   
                   <#list templateCompanyAddress as item>
                     ${item}
                     <br/>
      			   </#list>
                  </p>
                  
            </div>
                <p
                  style="
                    color: #e67e17;
                    font-size : 14;
                    font-family: -moz-initial;
                  "
                >
                   <#list templateCompanyContact as item>
                     ${item}
                     <br/>
      			   </#list>
                  
                </p>       
         
        </div>
      </div>
     </div>

</div>
  </body>
</html>
