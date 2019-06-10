FROM node:10-alpine
MAINTAINER java@gpsolutions.com
ENV APP_ROOT /opt/dir

RUN mkdir /opt/dir
WORKDIR ${APP_ROOT}
ADD . ${APP_ROOT}

RUN npm install

#RUN npm ci
#RUN npm run build

CMD ["npm", "run", "serve"]