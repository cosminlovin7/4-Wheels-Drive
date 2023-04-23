export function getImgFormat(base64Img) {
    const firstCharacter = base64Img.charCodeAt(0);

    let format = '';
    switch (firstCharacter) {
    case 47:
        format = 'jpg';
        break;
    case 105:
        format = 'png';
        break;
    default:
        format = 'unknown';
        break;
    }

    console.log(`Image format: ${format}`);

    return format;
}

export function getImgUri(base64Img) {
    const imgFormat = getImgFormat(base64Img);
    if (imgFormat === 'unknown')
        return '';

    console.log(imgFormat);
    return `data:image/${imgFormat};base64,${base64Img}`;
}

export function convertToBase64(e) {
    const file = e.target.files[0];
    console.log(file);
    const reader = new FileReader();

    let base64Img = '';
    
    reader.onloadend = () => {
        base64Img = reader.result.toString();

        console.log('base64Img: ' + base64Img);
    }

    reader.readAsDataURL(file);
}