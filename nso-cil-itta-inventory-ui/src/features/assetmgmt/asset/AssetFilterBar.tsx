import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { ASSET_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { AssetFilter } from './types';

interface Props {
  value: AssetFilter;
  onChange: (next: AssetFilter) => void;
  onReset: () => void;
}

export function AssetFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: AssetFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Asset filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Asset"
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
      <FilterField label="Asset Tag">
        <Input
          aria-label="Asset Tag filter"
          value={value.assetTag ?? ''}
          onChange={(v) => patch({ assetTag: v || undefined })}
        />
      </FilterField>
      <FilterField label="Asset Class">
        <Input
          aria-label="Asset Class filter"
          value={value.assetClass ?? ''}
          onChange={(v) => patch({ assetClass: v || undefined })}
        />
      </FilterField>
      <FilterField label="Acquired On">
        <DateRange
          ariaLabel="Acquired On"
          from={value.acquiredOnFrom}
          to={value.acquiredOnTo}
          onChange={(from, to) => patch({ acquiredOnFrom: from, acquiredOnTo: to })}
        />
      </FilterField>
      <FilterField label="Purchase Price">
        <RangeInputs
          ariaLabel="Purchase Price"
          min={value.purchasePriceMin}
          max={value.purchasePriceMax}
          onChange={(min, max) => patch({ purchasePriceMin: min, purchasePriceMax: max })}
        />
      </FilterField>
      <FilterField label="Current Value">
        <RangeInputs
          ariaLabel="Current Value"
          min={value.currentValueMin}
          max={value.currentValueMax}
          onChange={(min, max) => patch({ currentValueMin: min, currentValueMax: max })}
        />
      </FilterField>
      <FilterField label="Custodian">
        <Input
          aria-label="Custodian filter"
          value={value.custodian ?? ''}
          onChange={(v) => patch({ custodian: v || undefined })}
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
          options={ASSET_LIFECYCLE_STAGE_OPTIONS}
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
      <FilterField label="Owner">
        <Input
          aria-label="Owner filter"
          value={value.owner ?? ''}
          onChange={(v) => patch({ owner: v || undefined })}
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
