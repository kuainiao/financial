@/*
    鼠标悬停页面标签 margin-left: 15px
@*/
<button  class="btn btn-primary" style="margin-left: 15px" onclick="${clickFun!}" id="${id!}">
    <i class="fa fa-search">${name}</i>
</button>
<div id="hover" style="border-radius: 10px; margin-left: 75%; display: none;z-index: 101;position: absolute;width:450px;height: 670px;border:solid #8D8D8D 1px;background-color: #FFFFFF;">
    <div style="margin-top: 3px;">
        <table  style="height: 100%;width: 100%;border: none;font-size: 14px;">
            <tr style="height: 50px !important">
                <td style="width: 30%;">&nbsp;&nbsp;会计期间:</td>
                <td style="margin-left: 0.5%">
                    <select style="width: 120px;height: 34px;border: 1px solid #e5e6e7;color: inherit;font-size: 14px;" id="start_period" name="start_period">
                        <option value="" selected="selected">全部</option>
                        <option value="1">2018年第1期</option>
                    </select>
                    &nbsp;&nbsp;至
                    <select style="margin-left: 5px;width: 120px;height: 34px;border: 1px solid #e5e6e7;color: inherit;font-size: 14px;" id="end_period" name="end_period">
                        <option value="" selected="selected">全部</option>
                        <option value="1">2018年第11期</option>
                    </select>
                </td>
            </tr>
            @if(isEmpty(posting)){
            <tr style="height: 50px">
                <td colspan="2">
                   <div style="border: solid #cccccc 1px ;float: left;margin-left: 1.5%">
                       <input type="radio" name="posting_status" checked="checked" value="" style="margin-left: 8px"/>&nbsp;全部
                       <input type="radio" name="posting_status"  value="0" style="margin-left: 8px"/>&nbsp;未过账
                       <input type="radio" name="posting_status"  value="1" style="margin-left: 8px" />&nbsp;已过账&nbsp;&nbsp;&nbsp;
                   </div>
                    <div style="border: solid #cccccc 1px ;float: left; margin-left:16px">
                        <input type="radio" name="approval_status" checked="checked" value="" style="margin-left: 8px"/>&nbsp;全部
                        <input type="radio" name="approval_status"  value="0" style="margin-left: 8px"/>&nbsp;未审批
                        <input type="radio" name="approval_status"  value="1" style="margin-left: 8px"/>&nbsp;已审批&nbsp;&nbsp;&nbsp;
                    </div>
                </td>
            </tr>
            @}
            <tr style="height: 50px">
                <td>&nbsp;&nbsp;业务日期:</td>
                <td>
                    <input type="text" class="form-control layer-date" id="business_date" name="business_date" style="width: 80%">
                </td>
            </tr>
            <tr style="height: 40px">
                <td>&nbsp;&nbsp;凭证字:</td>
                <td>
                    <select style=" width: 120px;height: 34px;border: 1px solid #e5e6e7;color: inherit;font-size: 14px;" id="document_word" name="document_word">
                        <option value="" selected="selected">全部</option>
                        <option value="1">记账凭证</option>
                        <option value="2">收账凭证</option>
                        <option value="3">付账凭证</option>
                        <option value="4">转账凭证</option>
                    </select>
                </td>
            </tr>
            <tr style="height: 40px">
                <td> &nbsp;&nbsp;制单人:</td>
                <td>
                    <select style=" width: 120px;height: 34px;border: 1px solid #e5e6e7;color: inherit;font-size: 14px;" id="prepared_by" name="prepared_by">
                        <option value="" selected="selected">请选择制单人</option>
                    </select>
                </td>
            </tr>
            <tr style="height: 40px">
                <td>&nbsp;&nbsp;审批人：</td>
                <td>
                    <select style=" width: 120px;height: 34px;border: 1px solid #e5e6e7;color: inherit;font-size: 14px;" id="approval_people" name="approval_people">
                        <option value="" selected="selected">请选择审批人</option>
                    </select>
                </td>
            </tr>
            <tr style="height: 40px">
                <td>&nbsp;&nbsp;过账人:</td>
                <td>
                <select style=" width: 120px;height: 34px;border: 1px solid #e5e6e7;color: inherit;font-size: 14px;" id="posting_people" name="posting_people">
                    <option value="" selected="selected">请选择过账人</option>
                </select>
                </td>
            </tr>
            <tr style="height: 50px">
                <td>&nbsp;&nbsp;摘要:</td>
                <td>
                    <input type="text" name="summary" id="summary" style="width: 88%"/>
                </td>
            </tr>
            <tr style="height: 40px">
                <td>&nbsp;&nbsp;会计科目：</td>
                <td>
                    <select style=" width: 120px;height: 34px;border: 1px solid #e5e6e7;color: inherit;font-size: 14px;" id="expense_account" name="expense_account">
                        <option value="">请选择会计科目</option>
                    </select>
                </td>
            </tr>
            <tr style="height: 50px">
                <td>&nbsp;&nbsp;凭证号:</td>
                <td style="margin-left: 0.5%">
                    <input type="text" style="text-align: center; width: 120px;height: 34px;border: 1px solid #e5e6e7;color: inherit;font-size: 14px;"  id="start_document_number" name="start_document_number"/>
                    &nbsp;&nbsp;至
                    <input type="text" style="text-align: center;margin-left: 5px;width: 120px;height: 34px;border: 1px solid #e5e6e7;color: inherit;font-size: 14px;" id="end_document_number" name="end_document_number"/>
                </td>
            </tr>
            <tr style="height: 50px">
                <td>&nbsp;&nbsp;贷方金额:</td>
                <td style="margin-left: 0.5%">
                    <input type="text" style="text-align: center;width: 120px;height: 34px;border: 1px solid #e5e6e7;color: inherit;font-size: 14px;" id="start_credit_amount" name="start_credit_amount"/>
                    &nbsp;&nbsp;至
                    <input type="text" style="text-align: center;margin-left: 5px;width: 120px;height: 34px;border: 1px solid #e5e6e7;color: inherit;font-size: 14px;" id="end_credit_amount" name="end_credit_amount"/>
                </td>
            </tr>
            <tr style="height: 50px">
                <td>&nbsp;&nbsp;借方金额:</td>
                <td style="margin-left: 0.5%">
                    <input type="text" style="text-align: center;width: 120px;height: 34px;border: 1px solid #e5e6e7;color: inherit;font-size: 14px;" id="start_debit_amount" name="start_debit_amount"/>
                    &nbsp;&nbsp;至
                    <input type="text"  style="text-align: center;margin-left: 5px;width: 120px;height: 34px;border: 1px solid #e5e6e7;color: inherit;font-size: 14px;" id="end_debit_amount" name="end_debit_amount">
                </td>
            </tr>
            <tr style="height: 50px">
                <td>&nbsp;&nbsp;原币金额:</td>
                <td style="margin-left: 0.5%">
                    <input type="text" style="text-align: center;width: 120px;height: 34px;border: 1px solid #e5e6e7;color: inherit;font-size: 14px;" id="start_original_currency" name="start_original_currency" />
                    &nbsp;&nbsp;至
                    <input type="text" style="text-align: center;margin-left: 5px;width: 120px;height: 34px;border: 1px solid #e5e6e7;color: inherit;font-size: 14px;" id="end_original_currency" name="end_original_currency"/>
                </td>
            </tr>
            <tr style="height:50px ">
                <td colspan="2">
                    <button type="button" name="submit" id="submit" value="" onclick="${clickSubmit!}" class="btn btn-primary button-margin" style="width: 76px;height: 37px; margin-left: 40px!important">提交</button>
                    <button type="button" name="resete" id="resete" value="" onclick="${clickResete!}" class="btn btn-primary button-margin" style="width: 76px;height: 37px;margin-left:60px !important;">重置</button>
                    <button type="button" name="remove" id="remove" value="" onclick="${clickRemove!}" class="btn btn-primary button-margin" style="width: 76px;height: 37px;margin-left:60px !important;">取消</button>
                </td>
            </tr>
        </table>
    </div>
</div>