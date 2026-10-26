import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { DISCOVERED_ASSET_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { DiscoveredAssetFilter } from './types';

interface Props {
  value: DiscoveredAssetFilter;
  onChange: (next: DiscoveredAssetFilter) => void;
  onReset: () => void;
}

export function DiscoveredAssetFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: DiscoveredAssetFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Discovered Asset filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Discovered Asset"
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
      <FilterField label="Job Id">
        <Input
          aria-label="Job Id filter"
          placeholder="UUID"
          value={value.jobId ?? ''}
          onChange={(v) => patch({ jobId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Asset Type">
        <Input
          aria-label="Asset Type filter"
          value={value.assetType ?? ''}
          onChange={(v) => patch({ assetType: v || undefined })}
        />
      </FilterField>
      <FilterField label="Fingerprint">
        <Input
          aria-label="Fingerprint filter"
          value={value.fingerprint ?? ''}
          onChange={(v) => patch({ fingerprint: v || undefined })}
        />
      </FilterField>
      <FilterField label="Matched Asset Id">
        <Input
          aria-label="Matched Asset Id filter"
          placeholder="UUID"
          value={value.matchedAssetId ?? ''}
          onChange={(v) => patch({ matchedAssetId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Effective From">
        <DateRange
          ariaLabel="Effective From"
          from={value.effectiveFromFrom}
          to={value.effectiveFromTo}
          onChange={(from, to) => patch({ effectiveFromFrom: from, effectiveFromTo: to })}
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
          options={DISCOVERED_ASSET_LIFECYCLE_STAGE_OPTIONS}
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
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
