import { expect, test } from '@playwright/test';

test.describe('Geography', () => {
  test('Country list renders', async ({ page }) => {
    await page.goto('/geography/countries');
    await expect(page.getByRole('heading', { name: 'Countrys' }).or(page.getByRole('heading', { name: 'Countryes' }))).toBeVisible();
  });

  test('Country create form is reachable', async ({ page }) => {
    await page.goto('/geography/countries/new');
    await expect(page.getByRole('form', { name: 'Country form' })).toBeVisible();
  });

  test('Region list renders', async ({ page }) => {
    await page.goto('/geography/regions');
    await expect(page.getByRole('heading', { name: 'Regions' }).or(page.getByRole('heading', { name: 'Regiones' }))).toBeVisible();
  });

  test('Region create form is reachable', async ({ page }) => {
    await page.goto('/geography/regions/new');
    await expect(page.getByRole('form', { name: 'Region form' })).toBeVisible();
  });

  test('City list renders', async ({ page }) => {
    await page.goto('/geography/cities');
    await expect(page.getByRole('heading', { name: 'Citys' }).or(page.getByRole('heading', { name: 'Cityes' }))).toBeVisible();
  });

  test('City create form is reachable', async ({ page }) => {
    await page.goto('/geography/cities/new');
    await expect(page.getByRole('form', { name: 'City form' })).toBeVisible();
  });

  test('Site list renders', async ({ page }) => {
    await page.goto('/geography/sites');
    await expect(page.getByRole('heading', { name: 'Sites' }).or(page.getByRole('heading', { name: 'Sitees' }))).toBeVisible();
  });

  test('Site create form is reachable', async ({ page }) => {
    await page.goto('/geography/sites/new');
    await expect(page.getByRole('form', { name: 'Site form' })).toBeVisible();
  });

  test('Building list renders', async ({ page }) => {
    await page.goto('/geography/buildings');
    await expect(page.getByRole('heading', { name: 'Buildings' }).or(page.getByRole('heading', { name: 'Buildinges' }))).toBeVisible();
  });

  test('Building create form is reachable', async ({ page }) => {
    await page.goto('/geography/buildings/new');
    await expect(page.getByRole('form', { name: 'Building form' })).toBeVisible();
  });
});
