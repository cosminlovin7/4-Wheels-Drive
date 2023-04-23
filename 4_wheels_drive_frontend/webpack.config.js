module.exports = {
    module: {
      rules: [
        {
          test: /\.(png|jpe?g|gif)$/i,
          use: [
            {
              loader: 'base64-img-loader',
              options: {
                limit: 8192,
              },
            },
          ],
        },
      ],
    },
  };
  