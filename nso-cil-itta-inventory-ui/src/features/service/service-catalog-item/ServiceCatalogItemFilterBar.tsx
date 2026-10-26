import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { SERVICE_CATALOG_ITEM_FAMILY_OPTIONS, SERVICE_CATALOG_ITEM_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { ServiceCatalogItemFilter } from './types';

interface Props {
  value: ServiceCatalogItemFilter;
  onChange: (next: ServiceCatalogItemFilter) => void;
  onReset: () => void;
}

export function ServiceCatalogItemFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: ServiceCatalogItemFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Service Catalog Item filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Service Catalog Item"
          placeholder="code, name or description"
          value={value.q ?? ''}
          onChange={(v) => patch({ q: v || undefined })}
        />
      </FilterField>
      <FilterField label="Status">
        <MultiSelect
          ariaLabel="Status filter"
          options={LIFECYCLE_OPTIONS}
          value={value.status ?? []}
          onChange={(v) => patch({ status: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Code">
        <Input
          aria-label="Code filter"
          value={value.code ?? ''}
          onChange={(v) => patch({ code: v || undefined })}
        />
      </FilterField>
      <FilterField label="Name">
        <Input
          aria-label="Name filter"
          value={value.name ?? ''}
          onChange={(v) => patch({ name: v || undefined })}
        />
      </FilterField>
      <FilterField label="External Ref">
        <Input
          aria-label="External Ref filter"
          value={value.externalRef ?? ''}
          onChange={(v) => patch({ externalRef: v || undefined })}
        />
      </FilterField>
      <FilterField label="Item Name">
        <Input
          aria-label="Item Name filter"
          value={value.itemName ?? ''}
          onChange={(v) => patch({ itemName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Family">
        <MultiSelect
          ariaLabel="Family filter"
          options={SERVICE_CATALOG_ITEM_FAMILY_OPTIONS}
          value={value.family ?? []}
          onChange={(v) => patch({ family: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Base Price">
        <RangeInputs
          ariaLabel="Base Price"
          min={value.basePriceMin}
          max={value.basePriceMax}
          onChange={(min, max) => patch({ basePriceMin: min, basePriceMax: max })}
        />
      </FilterField>
      <FilterField label="Billing Unit">
        <Input
          aria-label="Billing Unit filter"
          value={value.billingUnit ?? ''}
          onChange={(v) => patch({ billingUnit: v || undefined })}
        />
      </FilterField>
      <FilterField label="Orderable">
        <TriState
          ariaLabel="Orderable filter"
          value={value.orderable}
          onChange={(v) => patch({ orderable: v })}
        />
      </FilterField>
      <FilterField label="Effective To">
        <DateRange
          ariaLabel="Effective To"
          from={value.effectiveToFrom}
          to={value.effectiveToTo}
          onChange={(from, to) => patch({ effectiveToFrom: from, effectiveToTo: to })}
        />
      </FilterField>
      <FilterField label="Labels">
        <Input
          aria-label="Labels filter"
          value={value.labels ?? ''}
          onChange={(v) => patch({ labels: v || undefined })}
        />
      </FilterField>
      <FilterField label="Lifecycle Stage">
        <MultiSelect
          ariaLabel="Lifecycle Stage filter"
          options={SERVICE_CATALOG_ITEM_LIFECYCLE_STAGE_OPTIONS}
          value={value.lifecycleStage ?? []}
          onChange={(v) => patch({ lifecycleStage: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Source System">
        <Input
          aria-label="Source System filter"
          value={value.sourceSystem ?? ''}
          onChange={(v) => patch({ sourceSystem: v || undefined })}
        />
      </FilterField>
      <FilterField label="Region">
        <Input
          aria-label="Region filter"
          value={value.region ?? ''}
          onChange={(v) => patch({ region: v || undefined })}
        />
      </FilterField>
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
