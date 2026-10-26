import { expect, test } from '@playwright/test';

test.describe('Software and Licensing', () => {
  test('Software Image list renders', async ({ page }) => {
    await page.goto('/software/software-images');
    await expect(page.getByRole('heading', { name: 'Software Images' }).or(page.getByRole('heading', { name: 'Software Imagees' }))).toBeVisible();
  });

  test('Software Image create form is reachable', async ({ page }) => {
    await page.goto('/software/software-images/new');
    await expect(page.getByRole('form', { name: 'Software Image form' })).toBeVisible();
  });

  test('Software Version list renders', async ({ page }) => {
    await page.goto('/software/software-versions');
    await expect(page.getByRole('heading', { name: 'Software Versions' }).or(page.getByRole('heading', { name: 'Software Versiones' }))).toBeVisible();
  });

  test('Software Version create form is reachable', async ({ page }) => {
    await page.goto('/software/software-versions/new');
    await expect(page.getByRole('form', { name: 'Software Version form' })).toBeVisible();
  });

  test('Patch list renders', async ({ page }) => {
    await page.goto('/software/patchs');
    await expect(page.getByRole('heading', { name: 'Patchs' }).or(page.getByRole('heading', { name: 'Patches' }))).toBeVisible();
  });

  test('Patch create form is reachable', async ({ page }) => {
    await page.goto('/software/patchs/new');
    await expect(page.getByRole('form', { name: 'Patch form' })).toBeVisible();
  });

  test('License Key list renders', async ({ page }) => {
    await page.goto('/software/license-keies');
    await expect(page.getByRole('heading', { name: 'License Keys' }).or(page.getByRole('heading', { name: 'License Keyes' }))).toBeVisible();
  });

  test('License Key create form is reachable', async ({ page }) => {
    await page.goto('/software/license-keies/new');
    await expect(page.getByRole('form', { name: 'License Key form' })).toBeVisible();
  });

  test('License Pool list renders', async ({ page }) => {
    await page.goto('/software/license-pools');
    await expect(page.getByRole('heading', { name: 'License Pools' }).or(page.getByRole('heading', { name: 'License Pooles' }))).toBeVisible();
  });

  test('License Pool create form is reachable', async ({ page }) => {
    await page.goto('/software/license-pools/new');
    await expect(page.getByRole('form', { name: 'License Pool form' })).toBeVisible();
  });
});
