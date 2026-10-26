import { expect, test } from '@playwright/test';

test.describe('Services', () => {
  test('Service Catalog Item list renders', async ({ page }) => {
    await page.goto('/service/service-catalog-items');
    await expect(page.getByRole('heading', { name: 'Service Catalog Items' }).or(page.getByRole('heading', { name: 'Service Catalog Itemes' }))).toBeVisible();
  });

  test('Service Catalog Item create form is reachable', async ({ page }) => {
    await page.goto('/service/service-catalog-items/new');
    await expect(page.getByRole('form', { name: 'Service Catalog Item form' })).toBeVisible();
  });

  test('Service Instance list renders', async ({ page }) => {
    await page.goto('/service/service-instances');
    await expect(page.getByRole('heading', { name: 'Service Instances' }).or(page.getByRole('heading', { name: 'Service Instancees' }))).toBeVisible();
  });

  test('Service Instance create form is reachable', async ({ page }) => {
    await page.goto('/service/service-instances/new');
    await expect(page.getByRole('form', { name: 'Service Instance form' })).toBeVisible();
  });

  test('Service Order list renders', async ({ page }) => {
    await page.goto('/service/service-orders');
    await expect(page.getByRole('heading', { name: 'Service Orders' }).or(page.getByRole('heading', { name: 'Service Orderes' }))).toBeVisible();
  });

  test('Service Order create form is reachable', async ({ page }) => {
    await page.goto('/service/service-orders/new');
    await expect(page.getByRole('form', { name: 'Service Order form' })).toBeVisible();
  });

  test('Service Dependency list renders', async ({ page }) => {
    await page.goto('/service/service-dependencies');
    await expect(page.getByRole('heading', { name: 'Service Dependencys' }).or(page.getByRole('heading', { name: 'Service Dependencyes' }))).toBeVisible();
  });

  test('Service Dependency create form is reachable', async ({ page }) => {
    await page.goto('/service/service-dependencies/new');
    await expect(page.getByRole('form', { name: 'Service Dependency form' })).toBeVisible();
  });

  test('Service Endpoint list renders', async ({ page }) => {
    await page.goto('/service/service-endpoints');
    await expect(page.getByRole('heading', { name: 'Service Endpoints' }).or(page.getByRole('heading', { name: 'Service Endpointes' }))).toBeVisible();
  });

  test('Service Endpoint create form is reachable', async ({ page }) => {
    await page.goto('/service/service-endpoints/new');
    await expect(page.getByRole('form', { name: 'Service Endpoint form' })).toBeVisible();
  });
});
