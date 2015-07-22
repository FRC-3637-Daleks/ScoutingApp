function setDeleteListener(){
    $('.delete').click(function () {
        if($(this).hasClass('new')) {
            $(this).parent().parent().remove();
            num--;
        } else {
            if ($(this).parent().find('input[type=hidden]').val() == 'false') {
                console.log('Delete');
                $(this).parent().find('input[type=hidden]').val(true);
                $(this).removeClass('btn-danger');
                $(this).addClass('btn-warning ');
                $(this).attr('value', 'Undo');
            } else {
                console.log('Undo');
                $(this).parent().find('input[type=hidden]').val(false);
                $(this).removeClass('btn-warning ');
                $(this).addClass('btn-danger');
                $(this).attr('value', 'Delete');
            }
        }
    });
}

$(document).ready(function () {
    setDeleteListener();
});