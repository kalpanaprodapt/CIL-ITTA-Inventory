import { expect, test } from '@playwright/test';

test.describe('Asset and Finance', () => {
  test('Asset list renders', async ({ page }) => {
    await page.goto('/assetmgmt/assets');
    await expect(page.getByRole('heading', { name: 'Assets' }).or(page.getByRole('heading', { name: 'Assetes' }))).toBeVisible();
  });

  test('Asset create form is reachable', async ({ page }) => {
    await page.goto('/assetmgmt/assets/new');
    await expect(page.getByRole('form', { name: 'Asset form' })).toBeVisible();
  });

  test('Asset Tag list renders', async ({ page }) => {
    await page.goto('/assetmgmt/asset-tags');
    await expect(page.getByRole('heading', { name: 'Asset Tags' }).or(page.getByRole('heading', { name: 'Asset Tages' }))).toBeVisible();
  });

  test('Asset Tag create form is reachable', async ({ page }) => {
    await page.goto('/assetmgmt/asset-tags/new');
    await expect(page.getByRole('form', { name: 'Asset Tag form' })).toBeVisible();
  });

  test('Purchase Order list renders', async ({ page }) => {
    await page.goto('/assetmgmt/purchase-orders');
    await expect(page.getByRole('heading', { name: 'Purchase Orders' }).or(page.getByRole('heading', { name: 'Purchase Orderes' }))).toBeVisible();
  });

  test('Purchase Order create form is reachable', async ({ page }) => {
    await page.goto('/assetmgmt/purchase-orders/new');
    await expect(page.getByRole('form', { name: 'Purchase Order form' })).toBeVisible();
  });

  test('Invoice list renders', async ({ page }) => {
    await page.goto('/assetmgmt/invoices');
    await expect(page.getByRole('heading', { name: 'Invoices' }).or(page.getByRole('heading', { name: 'Invoicees' }))).toBeVisible();
  });

  test('Invoice create form is reachable', async ({ page }) => {
    await page.goto('/assetmgmt/invoices/new');
    await expect(page.getByRole('form', { name: 'Invoice form' })).toBeVisible();
  });

  test('Depreciation Schedule list renders', async ({ page }) => {
    await page.goto('/assetmgmt/depreciation-schedules');
    await expect(page.getByRole('heading', { name: 'Depreciation Schedules' }).or(page.getByRole('heading', { name: 'Depreciation Schedulees' }))).toBeVisible();
  });

  test('Depreciation Schedule create form is reachable', async ({ page }) => {
    await page.goto('/assetmgmt/depreciation-schedules/new');
    await expect(page.getByRole('form', { name: 'Depreciation Schedule form' })).toBeVisible();
  });
});
