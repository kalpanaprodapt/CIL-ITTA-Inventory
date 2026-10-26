import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { SOFTWARE_IMAGE_LIFECYCLE_STAGE_OPTIONS, SOFTWARE_IMAGE_CRITICALITY_OPTIONS } from './types';
import type { SoftwareImageFilter } from './types';

interface Props {
  value: SoftwareImageFilter;
  onChange: (next: SoftwareImageFilter) => void;
  onReset: () => void;
}

export function SoftwareImageFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: SoftwareImageFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Software Image filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Software Image"
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
      <FilterField label="Vendor Id">
        <Input
          aria-label="Vendor Id filter"
          placeholder="UUID"
          value={value.vendorId ?? ''}
          onChange={(v) => patch({ vendorId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Image Name">
        <Input
          aria-label="Image Name filter"
          value={value.imageName ?? ''}
          onChange={(v) => patch({ imageName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Size Mb">
        <RangeInputs
          ariaLabel="Size Mb"
          min={value.sizeMbMin}
          max={value.sizeMbMax}
          onChange={(min, max) => patch({ sizeMbMin: min, sizeMbMax: max })}
        />
      </FilterField>
      <FilterField label="Checksum">
        <Input
          aria-label="Checksum filter"
          value={value.checksum ?? ''}
          onChange={(v) => patch({ checksum: v || undefined })}
        />
      </FilterField>
      <FilterField label="Platform">
        <Input
          aria-label="Platform filter"
          value={value.platform ?? ''}
          onChange={(v) => patch({ platform: v || undefined })}
        />
      </FilterField>
      <FilterField label="Storage Path">
        <Input
          aria-label="Storage Path filter"
          value={value.storagePath ?? ''}
          onChange={(v) => patch({ storagePath: v || undefined })}
        />
      </FilterField>
      <FilterField label="Lifecycle Stage">
        <MultiSelect
          ariaLabel="Lifecycle Stage filter"
          options={SOFTWARE_IMAGE_LIFECYCLE_STAGE_OPTIONS}
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
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={SOFTWARE_IMAGE_CRITICALITY_OPTIONS}
          value={value.criticality ?? []}
          onChange={(v) => patch({ criticality: v.length ? v : undefined })}
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
