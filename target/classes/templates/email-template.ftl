<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Java Techie Mail</title>

    <style>

.preview-table {
  width: 100%;
  border:  solid black;
  border-collapse: collapse;
}



.preview-tr {
  padding: 8px;
  border: 1px solid black;
}

.preview-th-td {
  text-align: center;
  border:1px solid black;
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
    </style>
  </head>

  <body>
   


    <div>
        <div>
          <img src="C:\Users\SHERIN\git\JavaAccounts\Intuisyz_Accounts_Java\src\main\resources\invoice_header.jpg" alt="Loading..." />
        </div>
        <br />
       
        <div style="text-align:center">
          <p style="font-size: 18 ;font-weight:bold">
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
            SAC Code: 
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
            INTUISYZ TECHNOLOGIES
            <br />
            PRIVATE LIMITED <br />
            A/c No: 074205500060 <br />
            ICICI BANK, ANGAMALY, <br />
            IFSC: ICIC0000742 <br />
            Swift Code: ICICINBBNRI <br />
            PANNO: AADCI6383A <br />
          </p>
        </div>
        <br />
        <div style="padding-left: 60; padding-right: 60 ">
          <table class="preview-table">
            <tr class="preview-tr">
              <th class="preview-th-td">Sl No</th>
              <th class="preview-th-td">Description</th>
              <th class="preview-th-td">Qty</th>
              <th class="preview-th-td"> Amount</th>
              <!-- {/* <th class="preview-th-td">{'     bjhjghjgj   '}</th> */} -->
            </tr>

           <#list invoiceSubData as item>
                <tr class="preview-tr">
                  <td class="preview-th-td">${item?index+1}</td>
                  <td class="preview-th-td">${item.description}</td>
                  <td class="preview-th-td">${item.qty}</td>
                  <td class="preview-th-td">${item.amount}</td>
                  <!-- {/* <td class="preview-th-td">{'        '}</td> */} -->
                </tr>
           </#list>

            <tr class="preview-tr">
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
          <br />

          <div style=" padding-right: 110 ">
            <table class="preview-footer-table">
              <tr class="preview-footer-tr">
                <th class="preview-footer-th-td">
                  Name <br />
                  <br />
                  Sijin Stephen <br />
                  Managing <br />
                  Director
                </th>
                <th class="preview-footer-th-td">
                  Signature <br />
                  <br />
                  <br />
                  <br />
                  <br />
                </th>
                <th class="preview-footer-th-td">
                  Date
                  <br />
                  <br />
                  <br />
                   ${invDate}
                  <br />
                  <br />
                </th>
              </tr>
            </table>
          </div>
          <br></br>
        </div>
        <div>
          <img src="C:\Users\SHERIN\git\JavaAccounts\Intuisyz_Accounts_Java\src\main\resources\invoice_footer.jpg"  alt="Loading..." />
        </div>
      </div>










  </body>
</html>
